//
//  BPAPIGameplayDataReceived.h
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClientObject.h"

@interface BPAPIGameplayDataReceived : BPAPIClientObject
@property (nonatomic) NSData* covarEigen;
@property (nonatomic) NSData* meanImage;
@property (nonatomic) NSData* projectedImages;
@property (nonatomic) NSData* workFunctEigen;

-(id)initWithDict:(NSDictionary *)dict;
@end
