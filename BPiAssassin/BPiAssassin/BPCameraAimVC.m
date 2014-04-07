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

@synthesize cameraFeedView;
@synthesize killTargetBtn;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

#pragma mark - Camera Capture
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    //TO DO
    //Camera Capture here
    [self setUpCameraForUImage];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

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
    NSString* otherUUID;
    if ([[defaults objectForKey:@"myUUID"] isEqualToString:@"000cb6a0-6cfa-ae21-000c-b6a06cfaaee3"]){
        //This is John. Assign Robby
        otherUUID = @"000cb6a0-b5bc-3740-000c-b6a0b5bc383f";
    } else {
        otherUUID = @"000cb6a0-6cfa-ae21-000c-b6a06cfaaee3";
    }
    NSString *gameUUID = [defaults objectForKey:@"gameUUID"];
    NSString *myUUID = [defaults objectForKey:@"myUUID"];
    
    [[BPAPIClient sharedAPIClient] killUserForGameId:gameUUID forAssassinId:myUUID forVictimId:otherUUID];
    
}

- (void)setUpCameraForUImage {
    AVCaptureSession *session = [[AVCaptureSession alloc] init];
	session.sessionPreset = AVCaptureSessionPresetMedium;
	
	CALayer *viewLayer = self.cameraFeedView.layer;
	NSLog(@"viewLayer = %@", viewLayer);
	
	AVCaptureVideoPreviewLayer *captureVideoPreviewLayer = [[AVCaptureVideoPreviewLayer alloc] initWithSession:session];
	
	captureVideoPreviewLayer.frame = self.cameraFeedView.bounds;
	[self.cameraFeedView.layer addSublayer:captureVideoPreviewLayer];
	
	AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
	
	NSError *error = nil;
	AVCaptureDeviceInput *input = [AVCaptureDeviceInput deviceInputWithDevice:device error:&error];
	if (!input) {
		// Handle the error appropriately.
		NSLog(@"ERROR: trying to open camera: %@", error);
	}
	[session addInput:input];
	
	[session startRunning];
}


@end
