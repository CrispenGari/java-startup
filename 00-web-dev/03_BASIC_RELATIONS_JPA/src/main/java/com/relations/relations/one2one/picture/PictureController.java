package com.relations.relations.one2one.picture;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/pictures")
public class PictureController {
    private final PictureService service;
    @PostMapping(path = "/add")
    public ResponseEntity<Picture> createPicture(@RequestBody Picture picture){
        Picture _picture = this.service.createPicture(picture);
        return ResponseEntity.status(201).body(_picture);
    }

    @PutMapping(path = "/update/{pictureId}")
    public ResponseEntity<Picture> updatePicture(@RequestBody Picture picture,
                                                 @PathVariable("pictureId") Long pictureId
                                                 ){
        Picture _picture = this.service.getPicture(pictureId);
        _picture.setUrl(picture.getUrl());
        return ResponseEntity.status(201).body(this.service.updatePicture(_picture));
    }
}
