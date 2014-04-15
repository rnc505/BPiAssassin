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
@property (nonatomic, retain) AVCaptureSession *sesh;
@end

@implementation BPCameraAimVC

@synthesize cameraFeedView;
@synthesize killTargetBtn;

+ (id)allocWithRouterParams:(NSDictionary *)params {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:[NSBundle mainBundle]];
    BPCameraAimVC *instance = [storyboard instantiateViewControllerWithIdentifier:@"BPCameraAimVC"];
    
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

#pragma mark - Camera Capture
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    //TO DO
    //Camera Capture here
    if(self.navigationController) {
        [self.navigationController setNavigationBarHidden:YES];
    }
    [self setUpCameraForUImage];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)backBtnPressed:(id)sender {
//    [self performSegueWithIdentifier:@"viewTargetRequested" sender:self];
    [[Routable sharedRouter] open:@"gameInProgressHome"];
}

- (IBAction)killTargetBtnPressed:(id)sender {
    [self.sesh stopRunning];
    killTargetBtn.enabled = NO;
    [NSThread sleepForTimeInterval:2.f];
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
            [defaults setObject:@"Registered" forKey:@"CurrentUserStatus"];
            [defaults synchronize];
//            [[Routable sharedRouter] pop:NO];
//            [self performSegueWithIdentifier:@"userWon" sender:self];
//            [[Routable sharedRouter] open:@"youWon"];
            UIAlertView *winAlert = [[UIAlertView alloc] initWithTitle:@"You Won!" message:@"Congratulations, you won the game!" delegate:nil cancelButtonTitle:@"Close" otherButtonTitles:nil];
            [winAlert show];
            
//            [[Routable sharedRouter] open:@"homePage"];
        } else {
//            [self performSegueWithIdentifier:@"viewTargetRequested" sender:self];
            [[Routable sharedRouter] open:@"gameInProgressHome"];
        }
        
        
        
    }];
    //TO DO
    //REMOVE THIS LATER
    NSString* otherUUID;
    if ([[defaults objectForKey:@"myUUID"] isEqualToString:@"ff73785fb527f7bf2499a4ecaebf12b1295a38e7ad90869340813919807ded38"]){
        //This is John. Assign Robby
        otherUUID = @"c0279cad4d5eaf37366cc8ed5f00804eea3045c938ccdc9f4c01594f90430a79";
    } else {
        otherUUID = @"ff73785fb527f7bf2499a4ecaebf12b1295a38e7ad90869340813919807ded38";
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
    self.sesh = session;
}


@end
