//
//  BPAPIGameCreated.h
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClientObject.h"

@interface BPAPIGameCreated : BPAPIClientObject
@property (nonatomic, copy) NSString* gameId;
@property (nonatomic, copy) NSArray* images;
-(id)initWithDict:(NSDictionary *)dict;

@end
