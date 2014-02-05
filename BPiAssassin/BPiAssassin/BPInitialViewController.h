//
//  BPInitialViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPInitialViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate, UITextFieldDelegate> {
    UITextField *activeField;
}

@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) IBOutlet UIControl *contentView;

@property (strong, nonatomic) IBOutlet UIImageView *face1;
@property (strong, nonatomic) IBOutlet UITextField *faceName;

@property (strong, nonatomic) IBOutlet UIButton *addFaceBtn;

@property (nonatomic, assign) BOOL imageSelected;
@property (nonatomic, assign) BOOL nameEntered;


- (IBAction)takePhoto:(UIButton *)sender;
- (IBAction)selectPhoto:(UIButton *)sender;

//Handles keyboard use for text field
- (IBAction)textFieldReturn:(id)sender;
- (IBAction) clickedBackground;

@end
