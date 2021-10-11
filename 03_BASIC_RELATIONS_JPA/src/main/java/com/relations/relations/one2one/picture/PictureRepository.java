package com.relations.relations.one2one.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
