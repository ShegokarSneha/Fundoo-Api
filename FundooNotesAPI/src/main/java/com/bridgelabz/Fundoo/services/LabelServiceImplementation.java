package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.accesstoken.AccessToken;
import com.bridgelabz.Fundoo.dto.LabelDto;
import com.bridgelabz.Fundoo.exceptionhandling.BlankException;
import com.bridgelabz.Fundoo.exceptionhandling.NotFoundException;
import com.bridgelabz.Fundoo.model.Label;
import com.bridgelabz.Fundoo.model.User;
import com.bridgelabz.Fundoo.repository.LabelRepository;
import com.bridgelabz.Fundoo.repository.UserRepository;
import com.bridgelabz.Fundoo.result.ResponseCode;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@Service("LabelService")
public class LabelServiceImplementation implements LabelServiceInterface {

	@Autowired
	private AccessToken accessToken;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseCode responseCode;

	private ResponseStatus response;
	
	@Override
	public ResponseStatus createLabel(LabelDto labeldto, String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		System.out.println(already.get());
		
		already.orElseThrow(()->new NotFoundException("User Not Found To Create Label"));
		
		if (labeldto.getLabelname().isEmpty()) {
			throw new BlankException("Label Name Can Not Be Empty.");
		}
			Label label = modelMapper.map(labeldto, Label.class);
			label.setUserid(userid);
			label.setCreatetime(LocalDateTime.now());
			label.setUpdatetime(LocalDateTime.now());
			labelRepository.save(label);
			List<Label> labellist = already.get().getLabellist();
			if (!labellist.isEmpty()) {
				labellist.add(label);
				already.get().setLabellist(labellist);
			} else {
				new ArrayList<Label>();
				labellist.add(label);
				already.get().setLabellist(labellist);
			}
			userRepository.save(already.get());
			response = responseCode.getResponse(201, "Label Created Successfully...!", label);
			System.out.println("Label Created Successfully...!");
		
		return response;
	}
	
	@Override
	public ResponseStatus updateLabel(LabelDto labeldto, String token, String labelid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Label> already = labelRepository.findByLabelidAndUserid(labelid, userid);
		
		already.orElseThrow(()-> new NotFoundException("Label Not Found With Entered Credentials"));
		
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
			already.get().setLabelname(labeldto.getLabelname());
			already.get().setUpdatetime(LocalDateTime.now());
			labelRepository.save(already.get());
			Optional<User> alreadyuser = userRepository.findByUserid(userid);
			userRepository.save(alreadyuser.get());
			response = responseCode.getResponse(200, "Label Updated Successfully...!", already.get());
			System.out.println("Label Updated Successfully...!");
		
		return response;
	}

	@Override
	public ResponseStatus deleteLabel(String token, String labelid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Label> already = labelRepository.findByLabelidAndUserid(labelid, userid);
		
		already.orElseThrow(()-> new NotFoundException("Label Not Found With Entered Credentials"));
		
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
			labelRepository.delete(already.get());
			response = responseCode.getResponse(200, "Label Deleted Successfully....!", null);
			System.out.println("Label Deleted Successfully....!");
		
		return response;
	}

	@Override
	public ResponseStatus getAlllabels(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		already.orElseThrow(()->new NotFoundException("User Not Found To get Label List"));

		
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
			response = responseCode.getResponse(200, "Label List", already.get().getLabellist());
			System.out.println("LabelList Get Successfully");

		return response;
	}

}
