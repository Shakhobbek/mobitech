package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.PageModel;
import com.bezkoder.spring.security.postgresql.models.Status;
import com.bezkoder.spring.security.postgresql.models.Sticker;
import com.bezkoder.spring.security.postgresql.payload.StatusPayload;
import com.bezkoder.spring.security.postgresql.repository.StickerRepository;
import com.bezkoder.spring.security.postgresql.service.ExportService;
import com.bezkoder.spring.security.postgresql.utils.StickerService;
import com.bezkoder.spring.security.postgresql.utils.UpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	private ExportService exportService;

	@Autowired
	private StickerRepository stickerRepository;

	@Autowired
	private UpdateStatus updateStatus;

	@Autowired
	private StickerService stickerService;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}


	@PostMapping("/barcode")
//	@PreAuthorize("hasRole('ADMIN')")
	public void addOrder(@RequestBody Sticker sticker) {
		stickerRepository.save(sticker);
	}

	@GetMapping("/file/{super_id}")
	public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer super_id) {

		HttpHeaders headers = new HttpHeaders();
		String fname = MessageFormat.format(
				"- {0,date,dd-MM}.xlsx", new Object [] { new Date() } );
		headers.add("Content-Disposition", "attachment; filename=orders "+super_id+fname);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(exportService.downloadFile(super_id)));
	}

	@PostMapping("/state")
	public void updateStatus(@RequestBody StatusPayload status){
		updateStatus.updateTaskState(status);
	}

	@GetMapping("/stickers")
	public PageModel getOrders(@RequestParam Integer page, @RequestParam Integer size){
		return stickerService.getTasksWithQueryPageWithData(page,size);
	}

	@GetMapping("/orders/{client_id}")
	public PageModel getOrdersById(@PathVariable String client_id,
								   @RequestParam Integer page,
								   @RequestParam Integer size){
		return stickerService.getStickerByClient(client_id,page,size);
	}

	@GetMapping("stickers/not_opened")
	public PageModel getByNotOpened(@RequestParam Integer page, @RequestParam Integer size){
		return stickerService.getDataByNotOpened(page, size);
	}

	@GetMapping("stickers/status/{status}")
	public PageModel getByStatus(@PathVariable String status,
								 @RequestParam Integer page,
								 @RequestParam Integer size){
		return stickerService.getStickerByStatus(status,page,size);
	}

	@GetMapping("stickers/orderNumber")
	public Integer getNumber(){
		return stickerService.getlastId();
	}

}
