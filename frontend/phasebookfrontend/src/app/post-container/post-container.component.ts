import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClientService } from "../http-client.service";
import { FormBuilder, FormGroup } from '@angular/forms';


interface PostInterface { 
  own:boolean, 
  email:string, 
  pageNumber:number,
  postId?:number,
  creatorName?:string,
  creatorEmail?:string,
  creationDate?:Date,
  audience?:string,
  textMessage?:string,
  imageUrl?:string,
  videoUrl?:string,
  likes?:number,
  disLikes?:number,
  creatorImageUrl?:string,
  comments?:number,
  liked?:boolean,
  disLiked?:boolean,
  commented?:boolean
} 


@Component({
  selector: 'app-post-container',
  templateUrl: './post-container.component.html',
  styleUrls: ['./post-container.component.css', './post-container.form.component .css']
})
export class PostContainerComponent implements OnInit {

  postSubmitForm : FormGroup;

  showEditForm: boolean = false;

  @Input() own:boolean=false;
  @Input() email:string=""; 
  pageNumber: number=0;

  myPosts:PostInterface []=[];

  
  constructor(fb: FormBuilder, private route: ActivatedRoute, private router: Router, private httpClientService:HttpClientService) {
    this.postSubmitForm = fb.group({
      'audience' : [''],
      'file' : [''],
      'textMessage' : ['']
    })
   }


  requestPosts(){

  }

  ngOnInit(): void {
    //for http://localhost:4200/timline?dfds=sdfdsf#sfdfs
    console.log(this.route.snapshot.queryParams); //{dfds: "sdfdsf"}
    console.log(this.route.snapshot.fragment);    //sfdfs
    console.log(this.router.url);                 ///timline?dfds=sdfdsf#sfdfs
    // console.log(this.route.url);
    console.log(this.route.snapshot.params['email']) ;
    

    let postType: PostInterface={
      own:this.own, 
      email:this.email,
      pageNumber:this.pageNumber,
    
    }
    this.httpClientService.requestPosts(postType).subscribe(
      (response) => {
        var data = JSON.parse(response);
        for(var i = 0;i<data.length;i++){
          var postData = data[i];
          this.myPosts.push(postData); 
        }

        console.log(data);
        console.log(this.myPosts);

      },
      (error) => {
        console.error('error caught in component: '+error.message);
      }
    );

  }

  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.postSubmitForm.get('file')?.setValue(file);

    }
  }

  postFormSubmit(data:any){ //data:any

    const formData = new FormData();
    formData.append('file', this.postSubmitForm.get('file')?.value);
    formData.append('audience',this.postSubmitForm.get('audience')?.value);
    formData.append('textMessage',this.postSubmitForm.get('textMessage')?.value);

    
    // let myPost = this.httpClientService.uploadPost(formData);
    let myPost = this.httpClientService.uploadPost(formData);
    myPost.subscribe(
      (response) => {
        console.log(response);

        this.showEditForm=!this.showEditForm;

        this.myPosts.unshift(JSON.parse(response));

      },
      (error) => {
        console.error('email already exist '+error.message);
      }
    )

  }

  onScrollUp(ev: any) {
    console.log("onScrollUp called");
  }

  
  onScrollDown(ev: any) {
    console.log("scrolled down!!");
    let postType: PostInterface={
      own:this.own, 
      email:this.email,
      pageNumber:this.pageNumber++,
    
    }

     
  

    this.httpClientService.requestPosts(postType).subscribe(
      (response) => {
        var data = JSON.parse(response);
        for(var i = 0;i<data.length;i++){
          var postData = data[i];
          this.myPosts.push(postData); 
        }

        console.log(data);
        console.log(this.myPosts);

      },
      (error) => {
        console.error('error caught in component: '+error.message);
      }
    );

  }

  deletePost(postId: number) {
    //myPosts

    // this.myPosts.forEach(
    //   (element, index)=>{
    //     if(element.postId==postId){
    //       this.myPosts.splice(index,1);
    //     } 
    // });

    for(var index in this.myPosts){
      if(this.myPosts[index].postId==postId){
        this.myPosts.splice(+index, 1);
        // delete this.myPosts[index];
        break;
      }
    }
    console.log("in post container"+postId);
  }

}
