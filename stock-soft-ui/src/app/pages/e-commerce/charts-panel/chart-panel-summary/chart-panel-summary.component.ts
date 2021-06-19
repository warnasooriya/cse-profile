import { Component, Input } from '@angular/core';

@Component({
  selector: 'ngx-chart-panel-summary',
  styleUrls: ['./chart-panel-summary.component.scss'],
  template: `
    <div class="summary-container">
      <div *ngFor="let item of summary" title={{item.subtitle}}>
        <div>{{ item.title }}</div>
        <div class="h6">{{ item.value | number:'2.2'}}</div>
      </div>
    </div>
  `,
})
export class ChartPanelSummaryComponent {
  @Input() summary: { title: string; value: number }[];
}

