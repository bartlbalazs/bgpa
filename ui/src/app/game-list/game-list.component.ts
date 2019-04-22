import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent {

  @Input()
  maxHeight: number;

  @Input()
  listOf: string

  @Input()
  games: any[]

  constructor() { }

}
