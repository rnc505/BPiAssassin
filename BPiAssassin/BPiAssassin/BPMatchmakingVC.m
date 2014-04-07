//
//  BPMatchmakingVC.m
//  BPiAssassin
//
//  Created by John Rozier on 2/15/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPMatchmakingVC.h"
#import "BPAPIClient.h"
#import "BPNotifications.h"
#import "BPAPIClientObjects.h"
@interface BPMatchmakingVC ()

@end

/* Note: this class is going to be replaced most likely with a GameCenter
 *  matchmaking VC
 *
 *  This class currently just calls create game automatically with default data
 *      This will need to be fixed in the future.
 *
 */


@implementation BPMatchmakingVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (IBAction)createGameBtnPress:(id)sender {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    //TO DO
    //remove this and replace with GameCenter
    NSString* otherUUID;
    if ([[defaults objectForKey:@"myUUID"] isEqualToString:@"000cb5c1-abd0-ad1b-000c-b5c1abd0adf9"]){
        //This is John. Assign Robby
        otherUUID = @"000cb564-278c-668f-000c-b564278c67bd";
    } else {
        otherUUID = @"000cb5c1-abd0-ad1b-000c-b5c1abd0adf9";
    }
    
    
    
    __block id gameCreated = [[NSNotificationCenter defaultCenter] addObserverForName:kGameCreatedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        [[NSNotificationCenter defaultCenter] removeObserver:gameCreated];

        BPAPIGameCreated *gameCreated = [[note userInfo] objectForKey:@"event"];
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Yay game created successfully" message:[NSString stringWithFormat:@"Game id: %@, number of Images: %d",[gameCreated gameId],[gameCreated images].count] delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
        [alert show];
        
        [defaults setObject:[gameCreated gameId] forKey:@"gameUUID"];
        [defaults synchronize];
        
        [[BPAPIClient sharedAPIClient] startGameWithGameId:[defaults objectForKey:@"gameUUID"] withMeanImage:@"MeanImage000" withCovarEigen:@"CovarEigen00" withWorkFunctEigen:@"WorkFunctionEigen000" withProjectedImages:@"ProjectedImages0"];
        
    }];
    __block id gameStarted = [[NSNotificationCenter defaultCenter] addObserverForName:kGameStartedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        [[NSNotificationCenter defaultCenter] removeObserver:gameStarted];
        
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Yay game started successfully" message:[NSString stringWithFormat:@"Game id: %@",[defaults objectForKey:@"gameUUID"]] delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
        [alert show];
        
        [defaults setBool:YES forKey:@"gameInProgress"];
        [defaults synchronize];
       
        [[BPAPIClient sharedAPIClient] getTargetForGameId:[defaults objectForKey:@"gameUUID"] forUserId:[defaults objectForKey:@"myUUID"]];
        
    }];
    
    __block id targetReceived = [[NSNotificationCenter defaultCenter] addObserverForName:kTargetReceivedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        [[NSNotificationCenter defaultCenter] removeObserver:targetReceived];
        
        BPAPINewTargetReceived *target = [[note userInfo] objectForKeyedSubscript:@"event"];
        
        [defaults setObject:[target targetId] forKey:@"targetUUID"];
        [defaults setObject:[target targetCodename] forKey:@"targetCodename"];
        [defaults setObject:UIImagePNGRepresentation([target targetThumbnail]) forKey:@"targetThumbnail"];
        [defaults synchronize];
        
        [self performSegueWithIdentifier:@"gameSucessfullyStarted" sender:self];
    }];

    //TO DO
    //need to fix this once GameCenter is working
    [[BPAPIClient sharedAPIClient] createGameWithHostId:[defaults objectForKey:@"myUUID"] withAllPlayersId:@[otherUUID, [defaults objectForKey:@"myUUID"]]];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}




@end
