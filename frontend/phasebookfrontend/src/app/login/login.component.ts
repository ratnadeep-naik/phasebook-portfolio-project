import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {HttpClient, HttpParams, HttpHeaders} from '@angular/common/http'
import { HttpClientService } from "../http-client.service";
import { AuthGuardService } from "../auth-guard.service";
import { WebSocketAPIService } from "../web-socket-api.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login=true;
  show=false;
  userEmail:string='';
  userPassword:string='';

  signupForm : FormGroup;
  loginForm: FormGroup;
  
  constructor(fb: FormBuilder, private http: HttpClient, public httpClientService:HttpClientService, private authGuardService:AuthGuardService, private webSocketAPIService:WebSocketAPIService)  {
    this.signupForm = fb.group({
      'firstName' : [''],
      'lastName' : [''],
      'email' : [''],
      'password' : ['']
    })
    this.loginForm = fb.group({
      'email' : [''],
      'password' : ['']
    });

    this.userEmail= this.loginForm.controls['email'].value;
    this.userPassword= this.loginForm.controls['password'].value;

   }

  ngOnInit(): void {
  }
  toggleShow(){
    this.show = !this.show;
  }
loginFormSubmit(value: string): void{

  let resp=this.httpClientService.generateToken(value);
  resp.subscribe(
    (response) => {
      this.authGuardService.jwtKeyWithBearer='Bearer ' + response;
      this.httpClientService.showUserProfile(this.loginForm.controls['email'].value).subscribe(
        (response1) => {
          this.authGuardService.password=this.loginForm.controls['password'].value;
          this.authGuardService.userDetail=JSON.parse(response1);
          this.authGuardService.isLoggedIn=true;
          console.log("login key is "+response);
          console.log(this.authGuardService.userDetail.own);
          this.webSocketAPIService._connect();
        },
        (error) => {
          console.error('email already exist '+error.message);
        }
      )
    },
    (error) => {
      console.error('error caught in component: '+error.message);
      this.authGuardService.jwtKeyWithBearer='';
      this.authGuardService.isLoggedIn=false;
    }
  )
}

signupFormSubmit(value: string): void{
  let resp=this.httpClientService.signup(value);
  resp.subscribe(
    (response) => {
      // this.loginFormSubmit(value);
      // console.log(response);
      this.httpClientService.generateToken(value).subscribe(
        (response) => {
          this.authGuardService.jwtKeyWithBearer='Bearer ' + response;
          this.httpClientService.showUserProfile(this.signupForm.controls['email'].value).subscribe(
            (response1) => {
              this.authGuardService.password=this.signupForm.controls['password'].value;
              this.authGuardService.userDetail=JSON.parse(response1);
              this.authGuardService.isLoggedIn=true;
              console.log("login key is "+response);
              console.log(this.authGuardService.userDetail.own);
              this.webSocketAPIService._connect();
            },
            (error) => {
              console.error('email already exist '+error.message);
            }
          )
        },
        (error) => {
          console.error('error caught in component: '+error.message);
          this.authGuardService.jwtKeyWithBearer='';
          this.authGuardService.isLoggedIn=false;
        }
    
      )
    },
    (error) => {
      console.error('email already exist '+error.message);
    }
  )
}
}
