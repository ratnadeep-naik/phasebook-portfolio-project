import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClientService } from "../http-client.service";
import { WebSocketAPIService } from "../web-socket-api.service";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-websocket-experiment',
  templateUrl: './websocket-experiment.component.html',
  styleUrls: ['./websocket-experiment.component.css']
})
export class WebsocketExperimentComponent implements OnInit {

  messages:string[]=[] ; 

  chatForm : FormGroup;

  webSocketEndPoint: string = 'http://localhost:8080/news';
  topic: string = "";   //"/topic/user1";
  stompClient: any=null;
  jwtKey:string='';
  from:string='';

  constructor(fb: FormBuilder,  private httpClientService:HttpClientService, private httpClient: HttpClient) { 
    this.chatForm = fb.group({
      'from' : [''],
      'to' : [''],
      'message' : ['']
    })

  }

  setFrom(from:string){
    this.from=from;
    this.topic="/topic/"+from;
    this.httpClient.post<string>("http://localhost:8080/api/auth/signin",{"username":from, "password":"youcancallmex"},{  responseType: 'text' as 'json' })
    .subscribe(
     (response) => {
      this.jwtKey=response;
      console.log(response);
      this._connect();
     },
     (error) => {
       console.error('error caught in component: '+error.message);
     }
    );
  }

  ngOnInit(): void {

  }

  chatFormSubmit(value: any): void{
    // let tokenStr = this.authGuardService.jwtKeyWithBearer;
     const headers = new HttpHeaders().set('Authorization', this.jwtKey);
    this.httpClient.post<string>("http://localhost:8080/api/socket/sendMessage", value, {headers,  responseType: 'text' as 'json' })
    .subscribe(
      (response) => {
        console.log(response);
       },
       (error) => {
         console.error('error caught in component: '+error.message);
       }
    );
  }  

//-----------------------------------websocket related--------------------------------------
_connect() {
    this._disconnect();
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({"Authorization":"Bearer "+this.jwtKey}, function (frame: any) {
        _this.stompClient.subscribe(_this.topic, function (sdkEvent: any) {
            _this.onMessageReceived(sdkEvent);
        });
    }, this.errorCallBack);
};

_disconnect() {
    if (this.stompClient !== null) {
        this.stompClient.disconnect();
    }
    console.log("Disconnected");
}

// on error, schedule a reconnection attempt
errorCallBack(error: string) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
        this._connect();
    }, 5000);
}

/**
* Send message to sever via web socket
* @param {*} message 
*/
_send(message: any) {
    console.log("calling logout api via web socket");
    this.stompClient.send("/app/test/hello", {}, JSON.stringify(message));
}

onMessageReceived(message: string) {
    console.log("Message Recieved from Server :: " + message);
    this.messages.push(message);
}


}
