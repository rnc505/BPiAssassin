//
//  BPAppDelegate.m
//  BPiAssassin
//
//  Created by John Rozier on 1/22/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAppDelegate.h"
#import "UAirship.h"
#import "UAConfig.h"
#import "UAPush.h"
#import "BPAPIClient.h"
#import "BPNotifications.h"
#import "BPAPIClientObjects.h"
#import "BPViewControllers.h"

@implementation BPAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    _nav = [[UINavigationController alloc] initWithRootViewController:[BPLandingPage allocWithRouterParams:nil]];
    UAConfig *config = [UAConfig defaultConfig];
    [UAirship takeOff:config];
    [self registerRouting];
    [self updateStatus];
    [self.window setRootViewController:self.nav];
    return YES;
}

-(void)registerRouting {
    
    [[Routable sharedRouter] map:@"homePage" toController:[BPCreateGameVC class] withOptions:[UPRouterOptions root]];
    [[Routable sharedRouter] map:@"gameInProgressHome" toController:[BPLiveGameVC class] withOptions:[UPRouterOptions root]];
    [[Routable sharedRouter] map:@"gameStatsPage" toController:[BPGameStatsVC class] withOptions:[UPRouterOptions modal]];
    [[Routable sharedRouter] map:@"takeAimPage" toController:[BPCameraAimVC class] withOptions:[UPRouterOptions modal]];
    [[Routable sharedRouter] map:@"matchmakingPage" toController:[BPMatchmakingVC class] withOptions:[UPRouterOptions modal]];
    [[Routable sharedRouter] map:@"registerPage" toController:[BPRegisterUserVC class] withOptions:[UPRouterOptions modal]];
    [[Routable sharedRouter] map:@"diedPage" toController:[BPDeadViewController class] withOptions:[UPRouterOptions modal]];
    [[Routable sharedRouter] map:@"landingPage" toController:[BPLandingPage class] withOptions:[UPRouterOptions root]];
//    [[Routable sharedRouter] map:@"youWon" toController:[BPYouWonVC class]];
    [[Routable sharedRouter] setNavigationController:self.nav];

}

-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    UIApplicationState state = [application applicationState];
    if (state == UIApplicationStateActive) {
        [self updateStatus];
    }
}
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    //[self updateStatus];
//    [self.window.rootViewController viewDidAppear:NO];
    [self updateStatus];
    
}

-(void)updateStatus {
    NSString* uuid = [[NSUserDefaults standardUserDefaults] objectForKey:@"myUUID"];
    if(!uuid) {
        //        [self performSegueWithIdentifier:@"landingToRegistered" sender:self];
        [[Routable sharedRouter] open:@"homePage"];
    } else {
        
        __block id getGamePlayData = [[NSNotificationCenter defaultCenter] addObserverForName:kGamePlayDataReceivedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
            [[NSNotificationCenter defaultCenter] removeObserver:getGamePlayData];
            
            BPAPIGameplayDataReceived *gameplaydata = [[note userInfo] objectForKey:@"event"];
        }];
        
        
        
        __block id getTarget = [[NSNotificationCenter defaultCenter] addObserverForName:kTargetReceivedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
            [[NSNotificationCenter defaultCenter] removeObserver:getTarget];
            
            BPAPINewTargetReceived *target = [[note userInfo] objectForKey:@"event"];
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            [defaults setObject:[target targetId] forKey:@"targetUUID"];
            [defaults setObject:[target targetCodename] forKey:@"targetCodename"];
            [defaults setObject:UIImagePNGRepresentation([target targetThumbnail]) forKey:@"targetThumbnail"];
            [defaults synchronize];
            //            [self performSegueWithIdentifier:@"landingToAlive" sender:self];
            [[Routable sharedRouter] open:@"gameInProgressHome"];
            
        }];
        
        __block id statusReceived = [[NSNotificationCenter defaultCenter] addObserverForName:kUserStatusReceiviedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
            [[NSNotificationCenter defaultCenter] removeObserver:statusReceived];
            
            BPAPIUserStatusReceived *rec = [[note userInfo] objectForKey:@"event"];
            
            NSString *oldstatus = [[NSUserDefaults standardUserDefaults] objectForKey:@"CurrentUserState"];
            NSString *status = [rec status];
            //            [NSUserDefaults standardUserDefaults] setObject:@" forKey:
            if([status isEqualToString:@"Registered"]) {
                //                [self performSegueWithIdentifier:@"landingToRegistered" sender:self];
                [[Routable sharedRouter] open:@"homePage"];
            } else if([status isEqualToString:@"Playing - Dead"]) {
                //                [self performSegueWithIdentifier:@"landingToDead" sender:self];
                [[Routable sharedRouter] open:@"diedPage"];
            } else if([status isEqualToString:@"Playing - Alive"]) {
                if([oldstatus isEqualToString:@"Registered"]) {
                    [[BPAPIClient sharedAPIClient] getGamePlayDataForGameId:[rec gameId]];
                    [[BPAPIClient sharedAPIClient] getTargetForGameId:[rec gameId] forUserId:uuid];
                } else if ([oldstatus isEqualToString:@"Playing - Alive"]) {
                    [[BPAPIClient sharedAPIClient] getTargetForGameId:[rec gameId] forUserId:uuid];
                }
            }
            
            [[NSUserDefaults standardUserDefaults] setObject:[rec gameId] forKey:@"gameUUID"];
            [[NSUserDefaults standardUserDefaults] setObject:status forKey:@"CurrentUserState"];
            [[NSUserDefaults standardUserDefaults] synchronize];
            
        }];
        
        [[BPAPIClient sharedAPIClient] getUserStatusForId:uuid];
    }}

-(void)alertFailure:(NSString*)title description:(NSString*)description {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:description delegate:nil cancelButtonTitle:@"Close" otherButtonTitles:nil];
    [alert show];
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
