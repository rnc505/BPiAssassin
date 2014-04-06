//
//  BPCameraAimVC.m
//  BPiAssassin
//
//  Created by John Rozier on 2/15/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPCameraAimVC.h"
#import <AVFoundation/AVFoundation.h>
#import "BPAPIClient.h"
#import "BPNotifications.h"
#import "BPAPIClientObjects.h"

@interface BPCameraAimVC ()

@end

@implementation BPCameraAimVC

@synthesize killTargetBtn;

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
	// Do any additional setup after loading the view.
    
    //TO DO
    //NEED TO Write Code for Target Information
    AVCaptureSession *session = [[AVCaptureSession alloc] init];
    // Add inputs and outputs.
    [session startRunning];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Camera Capture



- (IBAction)backBtnPressed:(id)sender {
    [self performSegueWithIdentifier:@"viewTargetRequested" sender:self];
}

- (IBAction)killTargetBtnPressed:(id)sender {
    killTargetBtn.enabled = NO;
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    __block id nextTargetReceived = [[NSNotificationCenter defaultCenter] addObserverForName:kUserKilledNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        [[NSNotificationCenter defaultCenter] removeObserver:nextTargetReceived];
        
        BPAPINewTargetReceived *target = [[note userInfo] objectForKeyedSubscript:@"event"];
        
        [defaults setObject:[target targetId] forKey:@"targetUUID"];
        [defaults setObject:[target targetCodename] forKey:@"targetCodename"];
        [defaults setObject:UIImagePNGRepresentation([target targetThumbnail]) forKey:@"targetThumbnail"];
        [defaults synchronize];
        
        //TO DO
        //might need to change this depending, on how we handle
        //how a user is notified that they win.
        if ([[defaults objectForKey:@"targetUUID"] isEqualToString: [defaults objectForKey:@"myUUID"]]) {
            [defaults setBool:NO forKey:@"gameInProgress"];
            [defaults synchronize];
            
            [self performSegueWithIdentifier:@"userWon" sender:self];
        } else {
            [self performSegueWithIdentifier:@"viewTargetRequested" sender:self];
        }
        
        
        
    }];
    //TO DO
    //REMOVE THIS LATER
     NSString* robbysUUID = @"000b9508-2046-4c67-000b-950820464d44";
    NSString *gameUUID = [defaults objectForKey:@"gameUUID"];
    NSString *myUUID = [defaults objectForKey:@"myUUID"];
    
    [[BPAPIClient sharedAPIClient] killUserForGameId:gameUUID forAssassinId:myUUID forVictimId:robbysUUID];
    
}
@end
