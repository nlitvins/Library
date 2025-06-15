import {Component, OnInit} from '@angular/core';
import {Language, LanguageService} from '../../services/language.service';

@Component({
  selector: 'app-language-selector',
  template: `
    <div class="language-selector">
      <select [(ngModel)]="selectedLanguage" (change)="onLanguageChange()">
        <option value="en">English</option>
        <option value="lv">Latviešu</option>
      </select>
    </div>
  `,
  styles: [`
    .language-selector {
      padding: 8px;
    }
    select {
      padding: 4px 8px;
      border-radius: 4px;
      border: 1px solid #ccc;
      background-color: white;
      cursor: pointer;
    }
    select:hover {
      border-color: #666;
    }
  `]
})
export class LanguageSelectorComponent implements OnInit {
  selectedLanguage: Language = 'en';

  constructor(private languageService: LanguageService) {
  }

  ngOnInit() {
    this.selectedLanguage = this.languageService.getCurrentLanguage();
  }

  onLanguageChange() {
    this.languageService.setLanguage(this.selectedLanguage);
  }
}
