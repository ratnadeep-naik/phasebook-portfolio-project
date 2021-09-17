import { Component, OnInit, Input } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { HttpClientService } from "../http-client.service";
import { ActivatedRoute, Router } from '@angular/router';

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


@Component({
  selector: 'app-user-list-element',
  templateUrl: './user-list-element.component.html',
  styleUrls: ['./user-list-element.component.css']
})
export class UserListElementComponent implements OnInit {
  @Input() userDetail:UserDetailInterface={ own:false, friend:false, friendRequestSentByThisUser:false, friendRequestSentToThisUser:false, email: '', firstName: '', lastName: ''};

  constructor(public httpClientService:HttpClientService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFriendRequest(){
    console.log('sendFriendRequest called');
    this.httpClientService.sendFriendRequest(this.userDetail.email).subscribe(
      (response)=>{
        this.userDetail.friendRequestSentToThisUser=true;
      },
      (error)=>{
        console.log(error);
      }
    )

  }

  cancelSentFriendRequest(){
    console.log('cancelSentFriendRequest called');
    this.httpClientService.cancelSentFriendRequest(this.userDetail.email).subscribe(
      (response)=>{
        this.userDetail.friendRequestSentToThisUser=false;
      },
      (error)=>{
        console.log(error);
      }
    )

  }

  acceptFriendRequest(){
    console.log('acceptFriendRequest called');
    this.httpClientService.acceptFriendRequest(this.userDetail.email).subscribe(
      (response)=>{
        this.userDetail.friendRequestSentByThisUser=false;
        this.userDetail.friend=true;
      },
      (error)=>{
        console.log(error);
      }
    )

  }

  unfriend(){
    console.log('unfriend called');
    this.httpClientService.unFriend(this.userDetail.email).subscribe(
      (response)=>{
        this.userDetail.friend=false;
      },
      (error)=>{
        console.log(error);
      }
    )

  }

  chat(){
    console.log('chat called');

    // this.httpClientService.sendFriendRequest(this.userDetail.email).subscribe(
    //   (response)=>{
    //   },
    //   (error)=>{
    //     console.log(error);
    //   }
    // )
    this.router.navigate(['friendChats',this.userDetail.email]);

  }

}
