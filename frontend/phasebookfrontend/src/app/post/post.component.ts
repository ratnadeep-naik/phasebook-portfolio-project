import { Component, OnInit, Input } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClientService } from "../http-client.service";
import { FormBuilder, FormGroup } from '@angular/forms';


interface PostInterface { 
  own:boolean, 
  email:string, 
  pageNumber:number,
  postId:number,
  creatorName?:string,
  creationDate?:Date,
  creatorEmail?:string,
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
interface CommentInterface { 
	own:boolean;
	commentId:number;
	postId?: number;
  content?: string;
	creatorName: string;
	creatorEmail:string;
	creatorImageUrl: string;
	creationDate?: Date;
	pageNumber?: number;
} 


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  showComments:boolean=false;
  showImage:boolean=false;
  showVideo:boolean=false;
  commentPageNumber:number=0;

  @Input() myPosts:PostInterface={  own:false, email:'', pageNumber:0, postId:0};
  @Output() deletePost = new EventEmitter<string>();
  comments:CommentInterface[]=[{ own:false, commentId:0, creatorName: '', creatorEmail:'', creatorImageUrl: '', creationDate: new Date}];
  constructor(fb: FormBuilder, public httpClientService:HttpClientService) { }

  ngOnInit(): void {
    if(this.myPosts.imageUrl!=null){
      this.showImage=true;

    }else if(this.myPosts.videoUrl!=null){
      this.showVideo=true;
    }

  }

  delete(postId: string) {
    console.log("post id is "+postId);
    this.httpClientService.deletePost(postId).subscribe(
      (response) => {
        console.log(response);
        this.deletePost.emit(postId);
      },
      (error) => {
        console.error('error caught in component: '+error.message);
      }
    );
  }


  updateAudience(audience: string){ 
    this.httpClientService.updatePostAudience(audience, this.myPosts.postId).subscribe(
      (response) => {
        console.log(response);
        this.myPosts.audience=audience;
      },
      (error) => {
        console.error('error caught in component: '+error.message);
      }
    );
  }

  changeLikeStatus(){
    let previousDislikeStatus = this.myPosts.disLiked;
    if(this.myPosts.liked==true && this.myPosts.disLiked==false){
      this.httpClientService.likeOrDislikeOrDetachPost(true, true, this.myPosts.postId).subscribe(
        (response) => {
          this.myPosts.liked=false;
          this.myPosts.disLiked=false;
        },
        (error) => {
          console.error('error caught in component: '+error.message);
        }
      );
    }else{
      this.httpClientService.likeOrDislikeOrDetachPost(!this.myPosts.liked, false, this.myPosts.postId).subscribe(
        (response) => {
          this.myPosts.liked=!this.myPosts.liked;
          this.myPosts.disLiked=false;
        },
        (error) => {
          console.error('error caught in component: '+error.message);
        }
      );
    }
  }

  changeDisLikeStatus(){
    if(this.myPosts.disLiked==true && this.myPosts.liked==false){
      this.httpClientService.likeOrDislikeOrDetachPost(true, true, this.myPosts.postId).subscribe(
        (response) => {
          this.myPosts.liked=false;
          this.myPosts.disLiked=false;
        },
        (error) => {
          console.error('error caught in component: '+error.message);
        }
      );
    }else{
      this.httpClientService.likeOrDislikeOrDetachPost(!!this.myPosts.disLiked, false, this.myPosts.postId).subscribe(
        (response) => {
          this.myPosts.disLiked=!this.myPosts.disLiked;
          this.myPosts.liked=false;
        },
        (error) => {
          console.error('error caught in component: '+error.message);
        }
      );
    }
  }


  showCommentsByPageNumber(){
    this.httpClientService.showComments(this.myPosts.postId, this.commentPageNumber).subscribe(
      (response) => {
        var data = JSON.parse(response);
        for(var i = 0;i<data.length;i++){
          var postData = data[i];
          this.comments.push(postData); 
        }

        console.log(this.comments);

      },
      (error) => {
        console.error('error caught in component: '+error.message);
      }

    )
    
    this.commentPageNumber++;




  }

openOrCloseComments(){
  this.showComments=!this.showComments;
  this.commentPageNumber=0;
  this.comments = [];
}

commentSubmit(commentText:string){

 let comments:CommentInterface={ 	postId: this.myPosts.postId,content:commentText, own:false, commentId:0, creatorName: '', creatorEmail:'', creatorImageUrl: ''};
 this.httpClientService.submitComment2(comments).subscribe(
  (response) => {
    console.log(response);

    this.comments.unshift(JSON.parse(response));


  },
  (error) => {
    console.error('error caught in component: '+error.message);
  }
 )



  // console.log(this.myPosts.postId);
  // this.httpClientService.submitComment(this.myPosts.postId, commentText).subscribe(
    // (response) => {
    //   console.log(response);
    // },
    // (error) => {
    //   console.error('error caught in component: '+error.message);
    // }
  // )


  console.log(commentText);
}

}
