package com.bhq.ius.utils;

import com.bhq.ius.constant.IusConstant;
import com.bhq.ius.domain.entity.Profile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@Slf4j
class DataUtilTest {

    @InjectMocks
    DataUtil dataUtil;

    @Test
    void convertStringWithFormatDate() {
        String date = "19830327";
        String result = "1983-03-27";
        System.out.println("==== date ===== " + dataUtil.convertDateOfBirthWithFormat(date));
        assertEquals(result, dataUtil.convertDateOfBirthWithFormat(date));
    }

    @Test
    void convertStringToLocalDate() {
        String date = "1983-03-27";
        LocalDate result = LocalDate.of(1983, 03, 27);
        System.out.println("==== date ===== " + dataUtil.convertStringToLocalDate(date, "yyyy-MM-dd"));
        assertEquals(result, dataUtil.convertStringToLocalDate(date, "yyyy-MM-dd"));
    }

    @Test
    void convertLocalDateToString() {
        LocalDate date = LocalDate.of(1983, 03, 27);
        String expect = "19830327";
        System.out.println("==== date ===== " + dataUtil.convertLocalDateToString(date));
        assertEquals(expect, dataUtil.convertLocalDateToString(date));

    }

    @Test
    void base64ToBlob() throws SQLException {
        String base64 = "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";
        byte[] content = Base64.decodeBase64(base64);
        Profile profile = new Profile();
        profile.setImageFile(content);
        log.info("==== blob ==== {}", profile.getImageFile().length);
    }

    @Test
    void base64Jp2ToByteArrayOfJpgImage() throws IOException {
        String base64 = "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";
        byte[] result = DataUtil.base64Jp2toImageJpg(base64);
        log.info("==== result ===== {}", result.length);
    }
}