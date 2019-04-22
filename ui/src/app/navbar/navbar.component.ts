import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  searchUserId: string;
  displayUserId: string;

  statSubscription: Subscription;
  
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.statSubscription = this.apiService.userStats.subscribe(message => {
      this.displayUserId = message.userId
    })
  }

  ngOnDestroy(): void {
    this.statSubscription.unsubscribe();
  }

  onSubmit() {
    this.apiService.fetchStats(this.searchUserId)
    this.searchUserId = ''
  }
}
