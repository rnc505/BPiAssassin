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
    NSString* robbysUUID = @"000b9508-2046-4c67-000b-950820464d44";
    NSString* johnsUUID = @"000b93b7-ee30-75b4-000b-93b7ee3076f4";
    
    __block id gameCreated = [[NSNotificationCenter defaultCenter] addObserverForName:kGameCreatedNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        [[NSNotificationCenter defaultCenter] removeObserver:gameCreated];

        BPAPIGameCreated *gameCreated = [[note userInfo] objectForKey:@"event"];
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Yay game created successfully" message:[NSString stringWithFormat:@"Game id: %@, number of Images: %d",[gameCreated gameId],[gameCreated images].count] delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
        [alert show];
        
        
    }];
    
    [[BPAPIClient sharedAPIClient] createGameWithHostId:robbysUUID withAllPlayersId:@[robbysUUID, johnsUUID]];
    
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
