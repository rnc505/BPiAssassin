//
//  BPLiveGameVC.m
//  BPiAssassin
//
//  Created by John Rozier on 2/15/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPLiveGameVC.h"
#import "BPAPIClient.h"
#import "BPNotifications.h"
#import "BPAPIClientObjects.h"

@interface BPLiveGameVC ()

@end

@implementation BPLiveGameVC
@synthesize targetCodenameLabel;
@synthesize targetThumbnail;


+ (id)allocWithRouterParams:(NSDictionary *)params {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:[NSBundle mainBundle]];
    BPLiveGameVC *instance = [storyboard instantiateViewControllerWithIdentifier:@"BPLiveGameVC"];
    
    return instance;
}
- (IBAction)aimBtnPressed:(id)sender {
    [[Routable sharedRouter] open:@"takeAimPage"];
}
- (IBAction)gameStatsBtnPressed:(id)sender {
    [[Routable sharedRouter] open:@"gameStatsPage"];
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
	// Do any additional setup after loading the view.
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSDictionary *attributes = [(NSAttributedString *)targetCodenameLabel.attributedText attributesAtIndex:0 effectiveRange:NULL];
    targetCodenameLabel.attributedText = [[NSAttributedString alloc] initWithString:[defaults objectForKey:@"targetCodename"] attributes:attributes];
    NSData *imageData = [defaults objectForKey:@"targetThumbnail"];
    UIImage *image = [UIImage imageWithData:imageData];
    targetThumbnail.image = image;
    if(self.navigationController) {
        [self.navigationController setNavigationBarHidden:YES];
    }
}

- (void)viewDidAppear:(BOOL)animated {
//    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
//    NSDictionary *attributes = [(NSAttributedString *)targetCodenameLabel.attributedText attributesAtIndex:0 effectiveRange:NULL];
//    targetCodenameLabel.attributedText = [[NSAttributedString alloc] initWithString:[defaults objectForKey:@"targetCodename"] attributes:attributes];
//    NSData *imageData = [defaults objectForKey:@"targetThumbnail"];
//    UIImage *image = [UIImage imageWithData:imageData];
//    targetThumbnail.image = image;
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - View Rotation Lock
//Prevents this screen from being rotated into Landscape
- (NSUInteger)supportedInterfaceOrientations
{
    return UIInterfaceOrientationMaskPortrait + UIInterfaceOrientationMaskPortraitUpsideDown;
}

@end
