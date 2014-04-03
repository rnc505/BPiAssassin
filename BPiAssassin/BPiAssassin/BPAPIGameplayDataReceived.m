//
//  BPAPIGameplayDataReceived.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIGameplayDataReceived.h"
@interface BPAPIGameplayDataReceived()
@property (nonatomic, copy) NSString* covarEigenStr;

@property (nonatomic, copy) NSString* meanImageStr;

@property (nonatomic, copy) NSString* projectedImagesStr;

@property (nonatomic, copy) NSString* workFunctEigenStr;
@end

@implementation BPAPIGameplayDataReceived
-(id)initWithDict:(NSDictionary *)dict {
    self = [super initWithDict:dict];
    if (self) {
        _covarEigenStr = [[[dict objectForKey:@"covarEigen"] objectForKey:@"value"] copy];
        _meanImageStr = [[[dict objectForKey:@"meanImage"]objectForKey:@"value"] copy];
        _projectedImagesStr = [[[dict objectForKey:@"projectedImages"]objectForKey:@"value"] copy];
        _workFunctEigenStr = [[[dict objectForKey:@"workFunctEigen"]objectForKey:@"value"] copy];
        
    }
    return self;
}

-(NSData *)covarEigen {
    return [[NSData alloc] initWithBase64EncodedString:_covarEigenStr options:NSDataBase64DecodingIgnoreUnknownCharacters];
}

-(NSData *)meanImage {
    return [[NSData alloc] initWithBase64EncodedString:_meanImageStr options:NSDataBase64DecodingIgnoreUnknownCharacters];
}

-(NSData *)projectedImages {
    return [[NSData alloc] initWithBase64EncodedString:_projectedImagesStr options:NSDataBase64DecodingIgnoreUnknownCharacters];
}

-(NSData *)workFunctEigen {
    return [[NSData alloc] initWithBase64EncodedString:_workFunctEigenStr options:NSDataBase64DecodingIgnoreUnknownCharacters];
}

@end
