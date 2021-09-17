import { Component, OnInit,OnDestroy, ViewChild, ElementRef   } from '@angular/core';
import { HttpClientService } from "../http-client.service";
import { ActivatedRoute,Router } from '@angular/router';
import { WebSocketAPIService } from "../web-socket-api.service";
import { FormBuilder, FormGroup } from '@angular/forms';
import { ThisReceiver } from '@angular/compiler';

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
	lastAppearance?:Date,
  unread:number;
} 

interface UserAndChatDetailInterface { 
  chatId:number;
	from?:string;
  to?: string;
  content: Array<string>;
  contentType: Array<string>;
  timeStamp?:Date;
	firstName?: string;
	lastName?: string;
  photoUrl?:string,
  own?:boolean;
  status:number;
}

@Component({
  selector: 'app-user-chat-container',
  templateUrl: './user-chat-container.component.html',
  styleUrls: ['./user-chat-container.component.css']
})
export class UserChatContainerComponent implements OnInit, OnDestroy  {
  friendDetails:UserDetailInterface[]=[];
  currentFriendDetails:UserDetailInterface={own:false, friend:false, friendRequestSentByThisUser:false, friendRequestSentToThisUser:false, email:'', firstName:'', lastName:'', unread:2};
  chatsByFriend:UserAndChatDetailInterface[]=[];
  showChatSection:boolean=false;
  chatForm : FormGroup;
  to:string='';
  fileFirst:boolean=true;
  newMessageObs:any='';
  messageReceivedObs:any='';
  messageReadObs:any='';

  constructor(fb: FormBuilder, public httpClientService:HttpClientService, private router: Router, private activatedroute:ActivatedRoute, private webSocketAPIService:WebSocketAPIService) {
    
    this.chatForm = fb.group({
      'to' : [''],
      'content' : [''],
      'file' : [''],
      'fileFirst' : ['']
    })

    this.httpClientService.allFriends().subscribe(
      (response) => {
        var data = JSON.parse(response);
        for(var i = 0;i<data.length;i++){
          var friendData = data[i];
          this.friendDetails.push(friendData);
           this.friendDetails[i].unread=0;
        }
            this.unreadMessageNumberUpdater(this.webSocketAPIService.userAndChatDetail);
            this.currentFriendDetails = this.getFriendDetailByEmail(this.activatedroute.snapshot.params['email']);

          },
      (error) => {console.error(error.message);}
)
   }

   ngOnDestroy(): void {
    this.newMessageObs.unsubscribe();
    this.messageReceivedObs.unsubscribe();
    this.messageReadObs.unsubscribe();
    this.chatsByFriend=[];
  }

  ngOnInit(): void {
    this.newMessageObs = this.webSocketAPIService.arrayUpdate.subscribe(
    (data)=>{
      this.unreadMessageNumberUpdater(data);
      this.chatsByFriendUpdator(data);
    });

    this.messageReceivedObs = this.webSocketAPIService.messageRecevied.subscribe(
      (data)=>{
        this.messageStatusUpdate(data, 2);
      }
    )

    this.messageReadObs = this.webSocketAPIService.messageRead.subscribe(
      (data)=>{
        this.messageStatusUpdate(data, 3);
      }
    )

    this.activatedroute.url.subscribe(url =>{
      if(this.activatedroute.snapshot.params['email']===undefined){
        this.showChatSection=false;
      }else{
        this.to=this.activatedroute.snapshot.params['email'];
        this.showChatSection=true;
        this.chatsByFriend=[];
        this.currentFriendDetails = this.getFriendDetailByEmail(this.activatedroute.snapshot.params['email']);
        this.chatsByFriendUpdator(this.webSocketAPIService.userAndChatDetail);
        this.makeUnreadMessageNumberZero(this.activatedroute.snapshot.params['email']);
         this.loadChats();



      }
  });
  }
  getFriendDetailByEmail(email: string): UserDetailInterface {
    for(var i=0; i<this.friendDetails.length;i++){
      if(this.friendDetails[i].email==email){
        return this.friendDetails[i];
      }
    }
    return {own:false, friend:false, friendRequestSentByThisUser:false, friendRequestSentToThisUser:false, email:'', firstName:'', lastName:'', unread:2};
  }

  unreadMessageNumberUpdater(data:UserAndChatDetailInterface[]){
    for(var i=0; i<data.length; i++){
      let friendEmail = data[i].from;
      for(var j=0; j<this.friendDetails.length; j++){
        if(friendEmail==this.friendDetails[j].email && this.activatedroute.snapshot.params['email']!= friendEmail ){
          this.friendDetails[j].unread++;
          break;
        }
      }
    }
  }

  chatsByFriendUpdator(data:UserAndChatDetailInterface[]){
    let chatIds:number[]=[];
    let friend = this.to;
    for(var i=0; i<data.length; i++){
      if(data[i].from==friend || data[i].to==friend){
        chatIds.push(data[i].chatId);
        this.chatsByFriend.push(data[i]);
      }
    }

    this.webSocketArrayDeleter(chatIds);
    this.messageReadingConformationSender(chatIds);

  }

  webSocketArrayDeleter(chatIds:number[]){
    for(var i=0; i<chatIds.length; i++){
      let chatId :number = chatIds[i];
      for(var j=0; j<this.webSocketAPIService.userAndChatDetail.length; j++){
        if(this.webSocketAPIService.userAndChatDetail[j].chatId==chatId){
          this.webSocketAPIService.userAndChatDetail.splice(j,1);
          break;
        }
      }
    }
  }

  messageReadingConformationSender(chatIds:number[]){
    this.httpClientService.sendMessageReadConformation(chatIds).subscribe(
      (response)=>{
        console.log(JSON.parse(response));
      },
      (error)=>{
        console.log(error);
      }
    )
  }


  messageStatusUpdate(chatIds:number[], newStatus:number){
    for(var i=0; i<chatIds.length; i++){
      let chatId = chatIds[i];
      for(var j=0; j<this.chatsByFriend.length; j++){
        if(this.chatsByFriend[j].chatId==chatId){
          if(this.chatsByFriend[j].status<newStatus){
            this.chatsByFriend[j].status=newStatus;
          }
        }
      }
    }
  }

  loadChats(){
    //if message quantity is zero it will call by page 0
    if(this.chatsByFriend.length==0){
      this.httpClientService.requestMessageWithFriend(this.to).subscribe(
        (response)=>{
          let data:UserAndChatDetailInterface[]=JSON.parse(response);
          for(var i=0; i<data.length; i++){
            this.chatsByFriend.unshift(data[i]);
          }

        },
        (error)=>{
          console.log(error);
        }
      )
    }else{
      let chatId:number = this.chatsByFriend[0].chatId;
      this.httpClientService.requestMessageWithFriendAndId(this.to, chatId).subscribe(
        (response)=>{
          let data:UserAndChatDetailInterface[]=JSON.parse(response);
          for(var i=0; i<data.length; i++){
            this.chatsByFriend.unshift(data[i]);
          }
        },
        (error)=>{
          console.log(error);
        }

      )

    }
    //if message quantity is non zero it will call by last chatId

  }
  
  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.chatForm.get('file')?.setValue(file);
      if(this.chatForm.get('content')){
        //check fo file or text first
      }

    }
  }

  makeUnreadMessageNumberZero(email:string){
    for(var i=0; i<this.friendDetails.length; i++){
      if(this.friendDetails[i].email==email){
        this.friendDetails[i].unread=0;
      }
    }
  }

  chatFormSubmit(data:any){
    const formData = new FormData();
    formData.append('file', this.chatForm.get('file')?.value);
    formData.append('content',this.chatForm.get('content')?.value);
    formData.append('to', this.to);
    formData.append('fileFirst',String(this.fileFirst));

    this.httpClientService.communicate(formData).subscribe(
      (response) => {
         console.log(response);
        // this.webSocketAPIService.userAndChatDetail.push(JSON.parse(response));
        this.webSocketAPIService.updateUserAndChatDetailAndSendEvent(JSON.parse(response));
        this.chatForm.reset();
      },
      (error) => {
        console.error(error.message);
      }
    )
  }
}
