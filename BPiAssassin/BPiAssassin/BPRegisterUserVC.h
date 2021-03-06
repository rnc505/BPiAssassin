//
//  BPRegisterUserVC.h
//  BPiAssassin
//
//  Created by John Rozier on 4/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPRegisterUserVC : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate>

@property (weak, nonatomic) IBOutlet UITextField *usr_code_name;

@property (weak, nonatomic) IBOutlet UIImageView *usrImage1;
@property (weak, nonatomic) IBOutlet UIImageView *usrImage2;
@property (weak, nonatomic) IBOutlet UIImageView *usrImage3;
@property (weak, nonatomic) IBOutlet UIImageView *usrImage4;

@property (weak, nonatomic) IBOutlet UIButton *registerButton;
@property (weak, nonatomic) IBOutlet UILabel *label1;
@property (weak, nonatomic) IBOutlet UILabel *label2;
@property (weak, nonatomic) IBOutlet UILabel *label3;
@property (weak, nonatomic) IBOutlet UILabel *label4;
@end
