//
//  BPLandingPage.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/7/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPLandingPage.h"
#import "BPAPIClient.h"
#import "BPAPIClientObjects.h"
#import "BPNotifications.h"
@interface BPLandingPage ()

@end

@implementation BPLandingPage


+ (id)allocWithRouterParams:(NSDictionary *)params {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:[NSBundle mainBundle]];
    BPLandingPage *instance = [storyboard instantiateViewControllerWithIdentifier:@"BPLandingPage"];
    
    return instance;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationBarHidden = YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

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
    }
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
