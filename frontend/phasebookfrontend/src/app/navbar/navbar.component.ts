import { Component, OnInit } from '@angular/core';
import { AuthGuardService } from "../auth-guard.service";
import { HttpClientService } from "../http-client.service";
import { ActivatedRoute, Router } from '@angular/router';
import { WebSocketAPIService } from "../web-socket-api.service";
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  changeText=true;
  isLogin = true;
  constructor(private authGuardService:AuthGuardService, private httpClientService:HttpClientService, private router: Router, private route: ActivatedRoute, public webSocketAPIService:WebSocketAPIService) { 
    // this.webSocketAPIService.userAndChatDetail.length
  }

  
  logout(){
    this.webSocketAPIService._disconnect();
    this.authGuardService.jwtKeyWithBearer='';
    this.authGuardService.isLoggedIn=false;

  }

  ngOnInit(): void {
  }

  peopleSubmit(commentText:string){

    // this.httpClientService.submitComment2('').subscribe(
    //  (response) => {
    //    console.log(response);
    //  },
    //  (error) => {
    //    console.error('error caught in component: '+error.message);
    //  }
    // )
    // this.router.navigate([commentText], {relativeTo:this.route});
    this.router.navigate(['findUsers',commentText]);
    console.log(commentText);
  }

  // addArray(){
  //   this.webSocketAPIService.updateArray();
  // }

}
