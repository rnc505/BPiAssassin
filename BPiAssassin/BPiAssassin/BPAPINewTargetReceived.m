//
//  BPAPINewTargetReceived.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPINewTargetReceived.h"
@interface BPAPINewTargetReceived()
@property (nonatomic, retain) NSData* targetThumbnailData;
@end
@implementation BPAPINewTargetReceived
-(id)initWithDict:(NSDictionary *)dict {
    self = [super initWithDict:dict];
    if (self) {
        _targetCodename = [[dict objectForKey:@"code_name"] copy];
        _targetId = [[dict objectForKey:@"targetUUID"] copy];
        _targetThumbnailData = [[NSData alloc] initWithBase64EncodedString:[[[dict objectForKey:@"thumbnail"] objectForKey:@"image"] objectForKey:@"value"] options:NSDataBase64DecodingIgnoreUnknownCharacters];
    }
    return self;
}

-(UIImage *)targetThumbnail {
    return [UIImage imageWithData:_targetThumbnailData];
}
@end
