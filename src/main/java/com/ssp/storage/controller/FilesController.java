package com.ssp.storage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssp.storage.service.IFilesService;
import com.ssp.storage.web.ResponseEntity;

@RestController
@RequestMapping("/api/files")
public class FilesController {

	Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	IFilesService fileService;

	@PostMapping("/addFile")
	public ResponseEntity<?> addFile(@RequestBody MultipartFile file, @RequestHeader String parent,
			@RequestHeader String userName) {
		return new ResponseEntity<>(fileService.addFile(file, parent, userName), HttpStatus.OK);
	}

	@GetMapping("/getFile")
	public ResponseEntity<?> getFile(@RequestHeader String folder, @RequestHeader String parentFolder,
			@RequestHeader String userName, @RequestHeader String fileName) {
		return new ResponseEntity<>(fileService.getFile(folder, parentFolder, userName, fileName), HttpStatus.OK);
	}
}
