import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userId: string;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.apiService.fetchStats(this.userId)
    this.userId = ''
  }
}
