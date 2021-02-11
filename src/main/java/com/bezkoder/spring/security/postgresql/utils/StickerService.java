package com.bezkoder.spring.security.postgresql.utils;

import com.bezkoder.spring.security.postgresql.models.PageModel;
import com.bezkoder.spring.security.postgresql.repository.StickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StickerService {
    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PageModel getTasksWithQueryPageWithData(Integer page, Integer size) {

        System.out.println("Page: " + page + ", Size: " + size);
        int offset = page * size - (size - 1);
        int stickerSize = getStickerSize();

        String sql = "select s.id, s.client_id, substring(cast(s.created_at as varchar(20)),6,11) as created_at,  " +
                "s.name, s.phone, s.address, s.comment, s.status from sticker s order by  s.id DESC " +
                "LIMIT ? OFFSET ?";
        Object list = this.jdbcTemplate.queryForList(sql, new Object[]{size,offset-1});
        return new PageModel(page,stickerSize,offset-1,size,list);
    }

    public PageModel getDataByNotOpened(Integer page, Integer size) {

        System.out.println("Page: " + page + ", Size: " + size);
        int offset = page * size - (size - 1);
        int stickerSize = getNotOpenedSize();

        String sql = "select s.id, s.client_id, substring(cast(s.created_at as varchar(20)),6,11) as created_at,  " +
                "s.name, s.phone, s.address, s.comment, s.status from sticker s where s.status = 'not_opened'  order by  s.created_at " +
                "LIMIT ? OFFSET ?";
        Object list = this.jdbcTemplate.queryForList(sql, new Object[]{size,offset-1});
        return new PageModel(page,stickerSize,offset-1,size,list);
    }

    public Integer getStickerSize() {

        String sql = "select count(*) from sticker";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer getNotOpenedSize() {
        String sql = "select count(*) from sticker where status='not_opened'";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public PageModel getStickerByClient(String client_id, Integer page, Integer size) {
        int offset = page * size - (size - 1);
        int taskSize = getStickerSizeByUser(client_id);
        String sql = " select s.id,substring(cast(s.created_at as varchar(20)),6,11) as created_at,  " +
                "s.name,  s.phone, s.address, s.comment, s.status from sticker s where cast(s.client_id as varchar(10)) = ?  order by  s.created_at DESC " +
                "LIMIT ? OFFSET ?";;
        Object list = jdbcTemplate.queryForList(sql, new Object[]{client_id, size, offset - 1});
        return new PageModel(page, taskSize, offset - 1, size, list);
    }

    public PageModel getStickerByStatus(String status, Integer page, Integer size) {
        int offset = page * size - (size - 1);
        int taskSize = getStickerSizeByStatus(status);
        String sql = " select s.id,substring(cast(s.created_at as varchar(20)),6,11) as created_at,  " +
                "s.name, s.client_id,s.phone, s.address, s.comment, s.status from sticker s where s.status = ?  order by  s.created_at DESC " +
                "LIMIT ? OFFSET ?";;
        Object list = jdbcTemplate.queryForList(sql, new Object[]{status, size, offset - 1});
        return new PageModel(page, taskSize, offset - 1, size, list);
    }

    public Integer getStickerSizeByStatus(String status) {

        String sql = "select count(*) from sticker where status=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{status}, Integer.class);
    }

    public Integer getStickerSizeByUser(String client_id) {

        String sql = "select count(*) from sticker where cast(client_id as varchar(20))=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{client_id}, Integer.class);
    }

    public  Integer getlastId(){
        String sql = "SELECT id+1\n" +
                "FROM sticker \n" +
                "ORDER BY id DESC \n" +
                "LIMIT 1 ";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }


}
