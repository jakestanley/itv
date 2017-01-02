package uk.co.jakestanley.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jakestanley.data.Offer;
import uk.co.jakestanley.exceptions.BadOfferException;
import uk.co.jakestanley.exceptions.EntityExistsException;
import uk.co.jakestanley.exceptions.NotFoundException;
import uk.co.jakestanley.services.OfferService;

@RestController
@RequestMapping(value="/offer")
public class OfferController {

	@Autowired
	OfferService offerService;
	
	@RequestMapping(method=RequestMethod.POST)
	private ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
		
		try {
			offerService.addOffer(offer);
		} catch (EntityExistsException e) {
			return new ResponseEntity<Offer>(offer, HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			return new ResponseEntity<Offer>(offer, HttpStatus.NOT_FOUND);
		} catch (BadOfferException e) {
			return new ResponseEntity<Offer>(offer, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Offer>(offer, HttpStatus.OK);
	}	
}
