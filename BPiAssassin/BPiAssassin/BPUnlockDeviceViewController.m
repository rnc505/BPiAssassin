//
//  BPUnlockDeviceViewController.m
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPUnlockDeviceViewController.h"
#import "BPConfirmViewController.h"

@interface BPUnlockDeviceViewController ()

@end

@implementation BPUnlockDeviceViewController

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([segue.identifier isEqualToString:@"faceConfirmedSegue"]) {
        BPConfirmViewController *nextVC = [segue destinationViewController];
        nextVC.face = self.targetFace.image;
        nextVC.faceName = self.faceName;
    }
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
    
    //Creates Welcome Message
    NSString *welcomeString = @"Welcome back ";
    welcomeString = [welcomeString stringByAppendingString: self.faceName];
    welcomeString = [welcomeString stringByAppendingString:@"!"];
    
    [self.welcomeLabel setText:welcomeString];  //Displays Welcome Message
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)takePhoto:(UIButton *)sender {
    //Custom Error Message for devices without cameras
    if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        UIAlertView *myAlertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                              message:@"Device has no camera"
                                                             delegate:nil
                                                    cancelButtonTitle:@"OK"
                                                    otherButtonTitles: nil];
        [myAlertView show];
    } else {
        UIImagePickerController *picker = [[UIImagePickerController alloc] init];
        picker.delegate = self;
        picker.allowsEditing = YES;
        picker.sourceType = UIImagePickerControllerSourceTypeCamera;
        
        [self presentViewController:picker animated:YES completion:NULL];
    }
}

- (IBAction)selectPhoto:(UIButton *)sender {
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    picker.delegate = self;
    picker.allowsEditing = YES;
    picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    
    [self presentViewController:picker animated:YES completion:NULL];
}

#pragma mark - Image Picker Controller delegate methods

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    
    UIImage *chosenImage = info[UIImagePickerControllerEditedImage];
    self.targetFace.image = chosenImage;
    
    self.unlockBtn.hidden = NO; //Unhides "Unlock" Button
    
    [picker dismissViewControllerAnimated:YES completion:NULL];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    
    [picker dismissViewControllerAnimated:YES completion:NULL];
    
}


@end
