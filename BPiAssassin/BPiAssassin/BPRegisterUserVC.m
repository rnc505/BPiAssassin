//
//  BPRegisterUserVC.m
//  BPiAssassin
//
//  Created by John Rozier on 4/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPRegisterUserVC.h"
#import <MobileCoreServices/MobileCoreServices.h>
#import "BPAPIClient.h"
#import "UAirship.h"
#import "UAPush.h"
#import "BPAPIClientObjects.h"
#import "BPNotifications.h"
#import "UIImage+Utils.h"
@interface BPRegisterUserVC ()
@property (nonatomic) UIImagePickerController *imagePickerController;
@property (nonatomic, assign) int temp;

@end

@implementation BPRegisterUserVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

-(void)addTapRecognizer:(UIView*)view {
    UITapGestureRecognizer * recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(UIImageViewTapped:)];
    [recognizer setNumberOfTapsRequired:1];
    [recognizer setNumberOfTapsRequired:1];
    [view addGestureRecognizer:recognizer];
    
}
- (IBAction)endedWriting:(id)sender {
    if(self.usrImage1.image != nil && self.usrImage2.image != nil && self.usrImage3.image != nil && self.usrImage4.image != nil && ![self.usr_code_name.text isEqualToString:@""]) {
        [self.registerButton setEnabled:true];
    }
}

-(void)imagePickerController:
(UIImagePickerController *)picker
didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    NSString *mediaType = info[UIImagePickerControllerMediaType];
    
    if (![mediaType isEqualToString:(NSString *)kUTTypeImage])
    {
        @throw @"WHAT?";
    }
    
    UIImage *image = info[UIImagePickerControllerOriginalImage];
    switch (self.temp) {
        case 1:
            [self.label1 setHidden:YES];
            [self.usrImage1 setImage:image];
            break;
            
        case 2:
            [self.label2 setHidden:YES];
            [self.usrImage2 setImage:image];
            break;
            
        case 3:
            [self.label3 setHidden:YES];
            [self.usrImage3 setImage:image];
            break;
            
        case 4:
            [self.label4 setHidden:YES];
            [self.usrImage4 setImage:image];
            break;
            
        default:
            break;
    }
    if(self.usrImage1.image != nil && self.usrImage2.image != nil && self.usrImage3.image != nil && self.usrImage4.image != nil && ![self.usr_code_name.text isEqualToString:@""]) {
        [self.registerButton setEnabled:true];
    }
    [self dismissViewControllerAnimated:YES completion:nil];
}

-(void)imagePickerControllerDidCancel:
(UIImagePickerController *)picker
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSLog(@"Loaded");
    [self addTapRecognizer:self.usrImage1];
    [self addTapRecognizer:self.usrImage2];
    [self addTapRecognizer:self.usrImage3];
    [self addTapRecognizer:self.usrImage4];
    [self.registerButton setEnabled:false];
    [self.usr_code_name addTarget:self.usr_code_name
                           action:@selector(resignFirstResponder)
                 forControlEvents:UIControlEventEditingDidEndOnExit];
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
}
-(UIImagePickerController *)imagePickerController {
    if(!_imagePickerController) {
        _imagePickerController = [[UIImagePickerController alloc] init];
        _imagePickerController.sourceType = UIImagePickerControllerSourceTypeCamera;
        _imagePickerController.mediaTypes =
        @[(NSString *) kUTTypeImage];
        _imagePickerController.delegate = self;

    }
    return _imagePickerController;
}

-(void)UIImageViewTapped:(UIGestureRecognizer*)recognizer {
    if([[recognizer view] isEqual:self.usrImage1])
        self.temp = 1;
    else if([[recognizer view] isEqual:self.usrImage2])
        self.temp = 2;
    else if([[recognizer view] isEqual:self.usrImage3])
        self.temp = 3;
    else if([[recognizer view] isEqual:self.usrImage4])
        self.temp = 4;
    else
        @throw @"Frick";
    
    [self presentViewController:self.imagePickerController
                       animated:YES completion:nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

-(void)removeGestureRecognizers:(UIView*)view {
    for (UIGestureRecognizer* recognizer in [view gestureRecognizers]) {
        [view removeGestureRecognizer:recognizer];
    }
}

- (IBAction)registerButtonClicked:(UIButton *)sender {
    [self removeGestureRecognizers:self.usrImage1];
    [self removeGestureRecognizers:self.usrImage2];
    [self removeGestureRecognizers:self.usrImage3];
    [self removeGestureRecognizers:self.usrImage4];
    [self.registerButton setEnabled:false];
    
    __block id registerComplete = [[NSNotificationCenter defaultCenter] addObserverForName:kUserRegisteredNotification object:[BPAPIClient sharedAPIClient] queue:nil usingBlock:^(NSNotification *note) {
        // segue
        
        [[NSNotificationCenter defaultCenter] removeObserver:registerComplete];
        BPAPIUserRegistered* event = [[note userInfo] objectForKey:@"event"];
        [[NSUserDefaults standardUserDefaults] setObject:[event userId] forKey:@"myUUID"];
        [[NSUserDefaults standardUserDefaults] synchronize];
        [self performSegueWithIdentifier:@"userRegistered" sender:self];
                
    }];
    
    [[BPAPIClient sharedAPIClient] registerUserForUsername:[self.usr_code_name text] forThumbnail:[self.usrImage1.image resizedAndGrayscaledSquareImageOfDimension:200] forArrayOfFaceImages:@[[self.usrImage1.image resizedAndGrayscaledSquareImageOfDimension:300],[self.usrImage2.image resizedAndGrayscaledSquareImageOfDimension:300],[self.usrImage3.image resizedAndGrayscaledSquareImageOfDimension:300],[self.usrImage4.image resizedAndGrayscaledSquareImageOfDimension:300]] forApnDeviceToken:[[UAPush shared] deviceToken]];
    
}
#pragma mark - View Rotation Lock
//Prevents this screen from being rotated into Landscape
- (NSUInteger)supportedInterfaceOrientations
{
    return UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskPortraitUpsideDown;
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
