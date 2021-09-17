import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { HttpClientService } from "../http-client.service";

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
  selector: 'app-user-list-container',
  templateUrl: './user-list-container.component.html',
  styleUrls: ['./user-list-container.component.css']
})
export class UserListContainerComponent implements OnInit {
  product:any;
  pageNumber:number=0;
  userDetails:UserDetailInterface[]=[];

  constructor(private router: Router, private activatedroute:ActivatedRoute, private httpClientService:HttpClientService) {
  //   router.events.subscribe((val) => {
  //     // console.log("route param is "+this.activatedroute.snapshot.params['keyword']) ;
  // });

  this.activatedroute.url.subscribe(url =>{
    console.log("route param is "+this.activatedroute.snapshot.params['keyword']) ;
    this.pageNumber=0;
    this.httpClientService.people(this.pageNumber, this.activatedroute.snapshot.params['keyword']).subscribe(
      (response) => {
        var data = JSON.parse(response);
        for(var i = 0;i<data.length;i++){
          var postData = data[i];
          this.userDetails.push(postData); 
        }
        console.log(this.userDetails);
        this.pageNumber++;
      },
      (error) => {
        console.error(error.message);
      }

    )
});

   }

  ngOnInit(): void {
    // let data: Data = this.activatedRoute.snapshot.data;
    this.activatedroute.data.subscribe(data => {
      this.product=data;
      if(data.people=='friends'){
        this.httpClientService.friends(this.pageNumber).subscribe(
          (response) => {
            var data = JSON.parse(response);
            for(var i = 0;i<data.length;i++){
              var postData = data[i];
              this.userDetails.push(postData); 
            }
            console.log(this.userDetails);
            this.pageNumber++;
              },
          (error) => {
            console.error(error.message);
          }
        );

      }else if(data.people=='friendRequestsSent'){
        this.httpClientService.friendRequestsSent(this.pageNumber).subscribe(
          (response) => {
            var data = JSON.parse(response);
            for(var i = 0;i<data.length;i++){
              var postData = data[i];
              this.userDetails.push(postData); 
            }
            console.log(this.userDetails);
            this.pageNumber++;
              },
          (error) => {
            console.error(error.message);
          }
        )

      }else if(data.people=='friendRequestsRecevied'){
        this.httpClientService.friendRequestsReceived(this.pageNumber).subscribe(
          (response) => {
            var data = JSON.parse(response);
            for(var i = 0;i<data.length;i++){
              var postData = data[i];
              this.userDetails.push(postData); 
            }
            console.log(this.userDetails);
            this.pageNumber++;
              },
          (error) => {
            console.error(error.message);
          }

        )

      }else if(data.people=='desiredUser'){
        // console.log("route param is "+this.activatedroute.snapshot.params['keyword']) ;
        this.httpClientService.people(this.pageNumber, this.activatedroute.snapshot.params['keyword']).subscribe(
          (response) => {
            var data = JSON.parse(response);
            for(var i = 0;i<data.length;i++){
              var postData = data[i];
              this.userDetails.push(postData); 
            }
            console.log(this.userDetails);
            this.pageNumber++;
              },
          (error) => {
            console.error(error.message);
          }
        )
      }
  })
  }

}
