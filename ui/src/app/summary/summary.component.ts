import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { LinearGaugeConfig } from '../shared/ChartConfig';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnChanges {

  constructor() { }

  config: LinearGaugeConfig = new LinearGaugeConfig();

  @Input()
  summary: any

  ngOnChanges(changes: SimpleChanges): void {
    if (this.summary) {
      this.summary.daysNeeded = Math.floor(Number.parseInt(this.summary.minutesToPlay) / 1440);
      this.summary.hourdNeeded = Math.floor((Number.parseInt(this.summary.minutesToPlay) - this.summary.daysNeeded * 1440) / 60);
    }
  }
}
