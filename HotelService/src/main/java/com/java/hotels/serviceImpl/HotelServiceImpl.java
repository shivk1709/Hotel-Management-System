package com.java.hotels.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.hotels.daos.HotelDao;
import com.java.hotels.dtos.HotelDto;
import com.java.hotels.exceptions.ResourceNotFoundException;
import com.java.hotels.models.Hotel;
import com.java.hotels.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelDao hotelDao;

	@Override
	public HotelDto addHotel(HotelDto hotelDto) {
		// TODO Auto-generated method stub
		Hotel hotel = this.hotelDao.save(hotelDtoToHotel(hotelDto));
		return hoteltoHotelDto(hotel);
	}

	@Override
	public HotelDto getHotelById(long id) {
		// TODO Auto-generated method stub
		Hotel hotel = this.hotelDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Hotel found having id", id));
		return hoteltoHotelDto(hotel);
	}

	@Override
	public List<HotelDto> getAllHotelDetails() {
		// TODO Auto-generated method stub
		List<Hotel> allHotels = this.hotelDao.findAll();
		return allHotels.stream().map(hotel -> hoteltoHotelDto(hotel)).collect(Collectors.toList());
	}

	@Override
	public HotelDto updateHotelDetailsById(HotelDto hotelDto, long id) {
		// TODO Auto-generated method stub
		Hotel hotel = this.hotelDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found having id: ", id));
		hotel.setName(hotelDto.getName());
		hotel.setWebsite(hotelDto.getWebsite());
		hotel.setAbout(hotelDto.getAbout());
		hotel.setLocation(hotelDto.getLocation());
		return hoteltoHotelDto(this.hotelDao.save(hotel));
	}

	@Override
	public String deleteHotelById(Long id) {
		// TODO Auto-generated method stub
		this.hotelDao.deleteById(id);
		return "Hotel Deleted Successfully";
	}
	
	public HotelDto hoteltoHotelDto(Hotel hotel) {
		HotelDto hotelDto = new HotelDto();
		hotelDto.setId(hotel.getId());
		hotelDto.setLocation(hotel.getLocation());
		hotelDto.setName(hotel.getName());
		hotelDto.setWebsite(hotel.getWebsite());
		hotelDto.setAbout(hotel.getAbout());
		return hotelDto;
	}
	
	public Hotel hotelDtoToHotel(HotelDto hotelDto) {
		Hotel hotel = new Hotel();
		hotel.setId(hotelDto.getId());
		hotel.setName(hotelDto.getName());
		hotel.setWebsite(hotelDto.getWebsite());
		hotel.setLocation(hotelDto.getLocation());
		hotel.setAbout(hotelDto.getAbout());
		return hotel;
	}

}
