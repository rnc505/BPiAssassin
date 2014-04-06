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
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    //TO DO
    //Need to add killUser command and register activity
    
}
@end
