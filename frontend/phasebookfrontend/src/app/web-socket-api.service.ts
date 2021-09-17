import { Injectable,EventEmitter } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { AuthGuardService } from './auth-guard.service';
import { HttpClientService } from "./http-client.service";

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

@Injectable({
  providedIn: 'root'
})
export class WebSocketAPIService {
userAndChatDetail: UserAndChatDetailInterface[]=[];
// webSocketEndPoint: string = 'http://localhost:8080/ws';
webSocketEndPoint: string = 'http://phasebook-env.eba-7nzhqfz3.us-east-2.elasticbeanstalk.com/ws';
topic: string = "";   //"/topic/user1";
received:string='';
read:string='';
stompClient: any = null;
jwtKey: string = '';
from: string = '';
arrayUpdate : EventEmitter<UserAndChatDetailInterface[]> = new EventEmitter();
messageRecevied : EventEmitter<number[]> = new EventEmitter();
messageRead : EventEmitter<number[]> = new EventEmitter();

  constructor(private authGuardService: AuthGuardService, private httpClientService:HttpClientService) { }

  _connect() {
    this._disconnect();
    this.getUnreadMessages2();
    this.jwtKey=this.authGuardService.jwtKeyWithBearer;
    this.topic="/topic/" +this.authGuardService.userDetail.email;
    this.received="/topic/received/" +this.authGuardService.userDetail.email;
    this.read="/topic/read/" +this.authGuardService.userDetail.email;
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({ "Authorization": this.jwtKey }, function (frame: any) {

      _this.stompClient.subscribe(_this.topic, function (sdkEvent: any) {
        console.log(sdkEvent.body);
        _this.messageReceivingConformationSenderAndDataUpdator([JSON.parse(sdkEvent.body).chatId]);
        _this.updateUserAndChatDetailAndSendEvent(JSON.parse(sdkEvent.body));
      });

      _this.stompClient.subscribe(_this.received, function (sdkEvent: any) {
        _this.messageRecevied.emit([JSON.parse(sdkEvent.body)]);
        console.log([JSON.parse(sdkEvent.body)]);
      });

      _this.stompClient.subscribe(_this.read, function (sdkEvent: any) {
        _this.messageRead.emit([JSON.parse(sdkEvent.body)]);
        console.log(sdkEvent.body);
      });



    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  errorCallBack(error: string) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  // getUnreadMessages(){
  //   this.httpClientService.unReadMessages().subscribe(
  //     (response)=>{

  //       let receivedMessageIds:number[]=[];
  //       let data = JSON.parse(response);

  //       for(var i=0; i<data.length; i++){
  //         this.userAndChatDetail.push(data[i]);
  //         if(data[i].status==1){
  //           receivedMessageIds.push(data[i].chatId);
  //         }
  //       }
  //      this.messageReceivingConformationSenderAndDataUpdator(receivedMessageIds);
  //     },
  //     (error)=>{
  //       console.log(error);
  //     }
  //   )
  // }

  getUnreadMessages2(){
    this.httpClientService.unReadMessages().subscribe(
      (response)=>{

        let receivedMessageIds:number[]=[];
        let data = JSON.parse(response);

        for(var i=0; i<data.length; i++){
          this.userAndChatDetail.unshift(data[i]);
          if(data[i].status==1){
            receivedMessageIds.unshift(data[i].chatId);
          }
        }
       this.messageReceivingConformationSenderAndDataUpdator(receivedMessageIds);
      },
      (error)=>{
        console.log(error);
      }
    )
  }


  messageReceivingConformationSenderAndDataUpdator(receivedMessageIds:number[]){
    this.httpClientService.sendMessageReceivedConformation(receivedMessageIds).subscribe(
      (response)=>{
        let data = JSON.parse(response);
        for(var i=0; i<data.length; i++){
          let chatIdForUpdate :number =data[i].chatId;
          for(var j=0; j<this.userAndChatDetail.length; j++){
            if(this.userAndChatDetail[j].chatId==chatIdForUpdate){
              this.userAndChatDetail[j].status=2;
              break;
            }
          }
        }
      }, 
      (error)=>{
        console.log(error);
      }
    );
  }

  updateUserAndChatDetailAndSendEvent(data:UserAndChatDetailInterface){
    this.userAndChatDetail.push(data);
    this.arrayUpdate.emit([data]);
  }

  

}
