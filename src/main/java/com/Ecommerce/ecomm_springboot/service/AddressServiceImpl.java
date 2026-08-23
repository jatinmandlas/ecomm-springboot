package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.Payload.AddressDTO;
import com.Ecommerce.ecomm_springboot.exceptions.ResourceNotFoundException;
import com.Ecommerce.ecomm_springboot.model.Address;
import com.Ecommerce.ecomm_springboot.model.User;
import com.Ecommerce.ecomm_springboot.repository.AddressRepository;
import com.Ecommerce.ecomm_springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address=modelMapper.map(addressDTO,Address.class);
        List<Address>  addressList=user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress=addressRepository.save(address);
        return modelMapper.map(savedAddress,AddressDTO.class);


    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addressList=addressRepository.findAll();
        return addressList.stream()
                .map(address->modelMapper.map(address,AddressDTO.class)).toList();

    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Optional<Address> address= Optional.of(addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId)));
        return modelMapper.map(address,AddressDTO.class);
    }
    @Override
    public List<AddressDTO> getUserAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setPincode(addressDTO.getPincode());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuildingName(addressDTO.getBuildingName());

        Address updatedAddress = addressRepository.save(addressFromDatabase);

        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }
    @Transactional
    @Override
    public String deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address",
                                "addressId",
                                addressId
                        ));

        User user = address.getUser();

        user.getAddresses().remove(address);

        return "Address deleted successfully with addressId: " + addressId;
    }
}
