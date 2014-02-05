//
//  BPFaceConfirmViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPFaceConfirmViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate>

@property (strong, nonatomic) IBOutlet UIImageView *face2;
@property (strong, nonatomic) IBOutlet UILabel *nameDisplay;
@property (strong, nonatomic) NSString *faceName;

@property (strong, nonatomic) IBOutlet UIButton *confirmFaceBtn;

- (IBAction)takePhoto:(UIButton *)sender;
- (IBAction)selectPhoto:(UIButton *)sender;


@end
