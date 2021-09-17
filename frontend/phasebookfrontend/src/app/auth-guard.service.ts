import { Injectable } from '@angular/core';
interface UserDetailInterface { 
	own:boolean,
	friend:boolean,
	friendRequestSentByThisUser:boolean,
	friendRequestSentToThisUser:boolean,
	email: string,
	firstName: string,
	lastName: string,
	photoUrl?: string,
	address?: string,
	phone?: string,
	gender?: string,
	jobInfo?: string,
	academicInfo?: string,
	personalInfo?: string,
	dob?:Date,
	lastAppearance?:Date
} 


@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  public userDetail:UserDetailInterface={
    own:false,
    friend:false,
    friendRequestSentByThisUser:false,
    friendRequestSentToThisUser:false,
    email: 'dfsfdsf',
    firstName: '',
    lastName: ''
    };

  public jwtKeyWithBearer:string='';
  public password: string='';

  public isLoggedIn=false;

  constructor() { }

  isAuthenticated(){
    //it should see if token is too old it should use this token and see weather this work or not 
    //if not working set token to ''
  if(this.jwtKeyWithBearer==null || this.jwtKeyWithBearer==''){
    return false;
  }else{
    return true;
  }
}

}