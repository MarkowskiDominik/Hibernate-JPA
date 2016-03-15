package pl.spring.demo.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.spring.demo.service.LibraryService;

@Controller
@RequestMapping(value = "/library")
public class LibraryController {
	@Autowired
	private LibraryService libraryService;

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteLibrary(Map<String, Object> params, @PathVariable("id") Long libraryId) {
		libraryService.deleteLibrary(libraryId);
		return "bookList";
	}
}
