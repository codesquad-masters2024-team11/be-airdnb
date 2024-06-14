package com.yourbnb.image.service;

import com.yourbnb.accommodation.exception.AccommodationImageNotFoundException;
import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.exception.NullInvalidException;
import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.image.repository.AccommodationImageRepository;
import com.yourbnb.image.util.ImageMapper;
import com.yourbnb.image.util.S3Service;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationImageService {
    private final AccommodationImageRepository imageRepository;
    private final S3Service s3Service;

    /**
     * 숙소 이미지를 S3에 업로드하고, 이미지 정보를 데이터베이스에 저장한다.
     *
     * @param file 업로드할 MultipartFile 객체
     * @return 업로드된 이미지 정보와 이미지 URL을 포함하는 AccommodationImageDto 객체
     * @throws IOException 파일 처리 또는 업로드 중에 I/O 오류가 발생할 때
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AccommodationImageDto uploadAccommodationImage(MultipartFile file) throws IOException {
        String uploadName = generateUploadName(file);

        String imageUrl = s3Service.uploadImage(file, uploadName);
        AccommodationImage savedImage = imageRepository.save(new AccommodationImage(uploadName, imageUrl));

        log.info("숙소 이미지 저장 성공");
        return ImageMapper.toAccommodationImageDto(savedImage);
    }

    /**
     * 데이터베이스에서 숙소 이미지를 조회하고 존재하면 반환한다.
     *
     * @param id 숙소 이미지 아이디
     * @return 숙소 이미지 엔티티 객체
     * @throws AccommodationImageNotFoundException 숙소 이미지를 찾을 수 없는 경우
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public AccommodationImage getAccommodationImageByIdOrThrow(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new AccommodationImageNotFoundException(id));
    }

    private String generateUploadName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String extension = extractExtension(file);
        return uuid + "." + extension;
    }

    private String extractExtension(MultipartFile file) {
        try {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            int index = originalFilename.lastIndexOf(".");
            return originalFilename.substring(index + 1);
        } catch (NullPointerException e) {
            log.error("이미지 이름이 유효하지 않습니다.");
            throw new NullInvalidException();
        }
    }
}
