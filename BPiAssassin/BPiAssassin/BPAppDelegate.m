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

@implementation BPAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    UAConfig *config = [UAConfig defaultConfig];
    [UAirship takeOff:config];
    
    return YES;
}


-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    UIApplicationState state = [application applicationState];
    if (state == UIApplicationStateActive) {
        //the app is in the foreground, so here you do your stuff since the OS does not do it for you
        //navigate the "aps" dictionary looking for "loc-args" and "loc-key", for example, or your personal payload)
        //[self updateStatus];
        [self.window.rootViewController viewDidAppear:NO];
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
    [self.window.rootViewController viewDidAppear:NO];
    
}

-(void)updateStatus {
    if([[NSUserDefaults standardUserDefaults] objectForKey:@"CurrentUserState"] == nil || [[[NSUserDefaults standardUserDefaults] objectForKey:@"CurrentUserState"] isEqualToString:@"Unregistered"]) {
        [[NSUserDefaults standardUserDefaults] setObject:@"Unregistered" forKey:@"CurrentUserState"];
        [[NSUserDefaults standardUserDefaults] synchronize];
    } else {
        
        __block id status = [[NSNotificationCenter defaultCenter] addObserverForName:kUserStatusReceiviedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
            
            [[NSNotificationCenter defaultCenter] removeObserver:status];
            
            BPAPIUserStatusReceived* notification = [[note userInfo] objectForKey:@"event"];
            NSString *newStatus = [notification status];
            NSString *oldStatus =[[NSUserDefaults standardUserDefaults] objectForKey:@"CurrentUserState"];
            if(![newStatus isEqualToString:oldStatus]) {
                // so current status is incorrect...
                // therefore there is work to be done.
                UIViewController *top = self.window.rootViewController;
                BOOL worked = NO;
                if([newStatus isEqualToString:@"Playing - Alive"]) { // segue from waiting to start a game
                    @try {
                        [top performSegueWithIdentifier:@"createGameRequested" sender:top]; // assume that its BPCreateGameVC
                        worked = YES;
                    }
                    @catch (NSException *exception) {
                        /* not the corrected VC*/
                    }
                    @finally { }
                    if(!worked) {
                        @try {
                            [top performSegueWithIdentifier:@"gameSucessfullyStarted" sender:top]; // assume that is matchmaking
                            worked = YES;
                        }
                        @catch (NSException *exception) {
                            /* not the corrected VC*/
                            
                        }
                        @finally { }
                    }
                    if(!worked) {
                        [self alertFailure:@"Transitioning to 'Playing - Alive' Failed" description:@"Neither VC (BPCreateGameVC or BPMatchmakingVC) were open, so Segue failed"];
                    }
                } else if([newStatus isEqualToString:@"Playing - Dead"]) {
                    @try {
                        [top performSegueWithIdentifier:@"youGotKilled" sender:top]; // could be camera aim, game stats or live game
                        worked = YES;
                    }
                    @catch (NSException *exception) {
                        /* incorrect vc*/
                    }
                    @finally { }
                    if(!worked) {
                        [self alertFailure:@"Transitioning to 'Playing - Dead' Failed" description:@"None of the VCs were open, so segue failed"];
                    }
                } else if([newStatus isEqualToString:@"Registered"] && [oldStatus isEqualToString:@"Playing - Dead"]) {
                    @try {
                        [top performSegueWithIdentifier:@"deadToStart" sender:top];
                        worked = YES;
                    }
                    @catch (NSException *exception) {
                        /* incorrect vc */
                    }
                    @finally { }
                    if(!worked) {
                        [self alertFailure:@"Transitioning to 'Registered' from 'Playing - Dead' Failed" description:@"Dead VC wasn't up"];
                    }
                } else {
                    [self alertFailure:@"Didn't catch something correct" description:[NSString stringWithFormat:@"Oops some weird shit just happened. Here's the old status %@. Here's the new status %@",oldStatus,newStatus]];
                }
            }
            [[NSUserDefaults standardUserDefaults] setObject:newStatus forKey:@"CurrentUserState"];
            [[NSUserDefaults standardUserDefaults] synchronize];
            
        }];
        [[BPAPIClient sharedAPIClient] getUserStatusForId:[[NSUserDefaults standardUserDefaults] objectForKey:@"myUUID"]];
    }
}

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
