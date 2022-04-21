package com.relations.relations.one2one.picture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PictureService implements PictureServiceInterface{
    private final PictureRepository repository;
    @Override
    public Picture createPicture(Picture picture) {
        return this.repository.save(picture);
    }

    @Override
    public Picture updatePicture(Picture picture) {
        return this.repository.save(picture);
    }
    @Override
    public Picture getPicture(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new IllegalStateException("the picture does not exists"));
    }
}
