@Reg
Feature: testing google place api


Scenario: Create place in google place

Given user prepare a request with payload
Then User send "POST" request
And User get status reponse code "200"
And user validate "status" is "OK"
And user validate "scope" is "APP"



Scenario:  testing get Place by placeID API
 
Given user prepare a get request with payload
Then User send "GET" request
And User get status reponse code "200"
And user validate all the response



Scenario: testing update place api

Given user prepare a put request with payload
Then User send "PUT" request
And User get status reponse code "200"
And user validate response "msg" is "Address successfully updated"

Scenario: testing delete place API

Given user prepare a put delete with payload
Then User send "delete" request
And User get status reponse code "200"
And user validate response "status" is "OK"



