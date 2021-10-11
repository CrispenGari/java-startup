package com.relations.relations.one2one.picture;

import org.springframework.stereotype.Service;

@Service
public interface PictureServiceInterface {
    Picture createPicture(Picture picture);
    Picture updatePicture(Picture picture);
    Picture getPicture(Long id);
}
