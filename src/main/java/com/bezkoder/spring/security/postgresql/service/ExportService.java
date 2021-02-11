package com.bezkoder.spring.security.postgresql.service;

import com.bezkoder.spring.security.postgresql.models.Sticker;
import com.bezkoder.spring.security.postgresql.repository.StickerRepository;
import com.bezkoder.spring.security.postgresql.utils.ExcelExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {
    @Autowired
    private StickerRepository stickerRepository;
    public ByteArrayInputStream downloadFile(Integer super_id) {
        List<Sticker> stickers =  stickerRepository.findAll();
        try {
            ByteArrayInputStream stickerOut = ExcelExport.barcodeExcel(stickers,super_id);
            return stickerOut;
        } catch (IOException e) {}
        return null;
    }
}
