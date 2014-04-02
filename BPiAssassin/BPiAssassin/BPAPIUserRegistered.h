//
//  BPAPIUser.h
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BPAPIClientObject.h"
@interface BPAPIUserRegistered : BPAPIClientObject
@property (nonatomic, copy) NSString* userId;
-(id)initWithDict:(NSDictionary *)dict;
@end
