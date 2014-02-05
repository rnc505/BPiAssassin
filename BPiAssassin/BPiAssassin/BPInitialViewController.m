//
//  BPInitialViewController.m
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPInitialViewController.h"
#import "BPFaceConfirmViewController.h"

@interface BPInitialViewController ()
@end

@implementation BPInitialViewController

@synthesize faceName, face1, scrollView, contentView, addFaceBtn;

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    if([segue.identifier isEqualToString:@"confirmSegue"]){
        BPFaceConfirmViewController *nextVC = [segue destinationViewController];
        nextVC.faceName = self.faceName.text;
    }
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    //Setup for Keyboard Use
    [self registerForKeyboardNotifications];
    
    self.imageSelected =NO;
    self.nameEntered = NO;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidLayoutSubviews{
    [super viewDidLayoutSubviews];
    [self.scrollView layoutIfNeeded];
    self.scrollView.contentSize = self.contentView.bounds.size;
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

//Handles hiding the Keyboard After Use
- (IBAction)textFieldReturn:(id)sender {
    [sender resignFirstResponder];
}
- (IBAction) clickedBackground {
    [self.view endEditing:YES];
}

#pragma mark - Keyboard Notifications Setup
- (void)registerForKeyboardNotifications {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardDidShowNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
}

// Called when the UIKeyboardDidShowNotification is sent.
- (void)keyboardWasShown:(NSNotification*)aNotification {
    NSDictionary* info = [aNotification userInfo];
    CGSize kbSize = [[info objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    UIEdgeInsets contentInsets = UIEdgeInsetsMake(0.0, 0.0, kbSize.height, 0.0);
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
    
    // If active text field is hidden by keyboard, scroll it so it's visible
    CGRect aRect = self.view.frame;
    aRect.size.height -= kbSize.height;
    if (!CGRectContainsPoint(aRect, activeField.frame.origin) ) {
        [self.scrollView scrollRectToVisible:activeField.frame animated:YES];
    }
}
// Called when the UIKeyboardWillHideNotification is sent
- (void)keyboardWillBeHidden:(NSNotification*)aNotification {
    UIEdgeInsets contentInsets = UIEdgeInsetsZero;
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
    
    //Check to unhide "Add Face" Button
    if ([self.faceName.text isEqualToString: @""]) {
        self.nameEntered = NO;
        self.addFaceBtn.hidden =YES;
    } else if (self.imageSelected) {
        self.addFaceBtn.hidden = NO;
    } else {
        self.nameEntered = YES;
    }
}

/*#pragma mark - TextField delegate methods
- (void)textFieldDidBeginEditing:(UITextField *)textField {
    activeField = textField;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    activeField = nil;
    
    //Check to unhide "Add Face" Button
    if ([self.faceName.text isEqualToString: @""]) {
        self.nameEntered = NO;
    } else if (self.imageSelected) {
        self.addFaceBtn.hidden = NO;
    } else {
        self.nameEntered = YES;
    }
}
*/

#pragma mark - Image Picker Controller delegate methods
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    
    UIImage *chosenImage = info[UIImagePickerControllerEditedImage];
    self.face1.image = chosenImage;
    
    //Check to unhide "Add Face" Button
    self.imageSelected = YES;
    if (self.nameEntered)
        addFaceBtn.hidden = NO;
    
    [picker dismissViewControllerAnimated:YES completion:NULL];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    
    [picker dismissViewControllerAnimated:YES completion:NULL];
}

@end
