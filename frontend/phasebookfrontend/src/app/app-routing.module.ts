import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PostContainerComponent } from './post-container/post-container.component';
import { UserAccountContainerComponent } from './user-account-container/user-account-container.component';
import { UserListContainerComponent } from './user-list-container/user-list-container.component';
import { UserChatContainerComponent } from './user-chat-container/user-chat-container.component';
import { GroupAccountContainerComponent } from './group-account-container/group-account-container.component';
import { GroupListContainerComponent } from './group-list-container/group-list-container.component';
import { GroupChatContainerComponent } from './group-chat-container/group-chat-container.component';

import { ExperimentComponent } from './experiment/experiment.component';
import { WebsocketExperimentComponent } from './websocket-experiment/websocket-experiment.component';
import { WebsocketExperiment2Component } from "./websocket-experiment2/websocket-experiment2.component";

import {AuthGuard} from './auth.guard';


const appRoutes: Routes = [
  { path: '',  component: UserAccountContainerComponent },    //
    { path: 'userProfile/:email', canActivate: [AuthGuard],component: UserAccountContainerComponent },
  // { path: 'groupProfile/:groupId', canActivate: [AuthGuard], component: GroupAccountContainerComponent },
  { path: 'friendChats', component: UserChatContainerComponent },
  { path: 'friendChats/:email', component: UserChatContainerComponent },
  // { path: 'groupChats/:groupId', canActivate: [AuthGuard], component: GroupChatContainerComponent },
  { path: 'timeline', canActivate: [AuthGuard], component: PostContainerComponent },

  { path: 'findUsers/:keyword', canActivate: [AuthGuard], component: UserListContainerComponent, data :{ id:'1', people:"desiredUser"}},
  { path: 'friends', canActivate: [AuthGuard], component: UserListContainerComponent, data :{ id:'1', people:"friends"} }, 
  { path: 'friendRequestsSent', canActivate: [AuthGuard], component: UserListContainerComponent, data :{ id:'1', people:"friendRequestsSent"} },
  { path: 'friendRequestsRecevied', canActivate: [AuthGuard], component: UserListContainerComponent, data :{ id:'1', people:"friendRequestsRecevied"}},
  // { path: 'groupJoinRequestsReceived/:groupId', canActivate: [AuthGuard], component: UserListContainerComponent },
  // { path: 'groupJoinRequestsReceived/user/:email', canActivate: [AuthGuard], component: UserListContainerComponent },
  // { path: 'groupMembers/:groupId', canActivate: [AuthGuard], component: UserListContainerComponent },

  // { path: 'findGroups/:keyword', canActivate: [AuthGuard], component: GroupListContainerComponent },
  // { path: 'groupJoinRequestsSent', canActivate: [AuthGuard], component: GroupListContainerComponent },
  // { path: 'userCreatedGroups', canActivate: [AuthGuard], component: GroupListContainerComponent },
  // { path: 'userJoinedGroups', canActivate: [AuthGuard], component: GroupListContainerComponent },

  { path: 'experiment', canActivate: [AuthGuard], component: ExperimentComponent },
  { path: 'websocketexperiment', component: WebsocketExperimentComponent },
  { path: 'websocketexperiment2', component: WebsocketExperiment2Component },
  { path: '**', redirectTo: '' }




  //   { path: 'users', component: UsersComponent, children: [
//   { path: ':id/:name', component: UserComponent }
//   ] },
//   {
//     path: 'servers',
//     // canActivate: [AuthGuard],
//     canActivateChild: [AuthGuard],
//     component: ServersComponent,
//     children: [
//     { path: ':id', component: ServerComponent, resolve: {server: ServerResolver} },
//     { path: ':id/edit', component: EditServerComponent, canDeactivate: [CanDeactivateGuard] }
//   ] },
//   // { path: 'not-found', component: PageNotFoundComponent },
//   { path: 'not-found', component: ErrorPageComponent, data: {message: 'Page not found!'} },
//   { path: '**', redirectTo: '/not-found' }


 ];

@NgModule({
  imports: [
    // RouterModule.forRoot(appRoutes, {useHash: true})
    RouterModule.forRoot(appRoutes, {scrollPositionRestoration: 'enabled'})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
