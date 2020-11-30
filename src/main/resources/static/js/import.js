webix.i18n.setLocale("ru-RU");

function view_section(title) {
    return {
        view: 'template',
        type: 'section',
        template: title
    }
}

function processFiles() {
    webix.confirm('Вы действительно хотите запустить загрузку ЕГРЮЛ/ЕГРИП?')
        .then(
            function () {
                webix.ajax().get('/processFiles', ).then(function (data) {
                    if (data.text() === 'Ok') {
                        webix.message({
                            text: 'Расписание запуска установлено',
                            type: 'success'
                        });
                    } else {
                        webix.message({
                            text: 'Не удалось задать расписание запуска',
                            type: 'error'
                        });
                    }
                })
            }
        )
}


webix.ready(function() {
    webix.ui({
       container: 'app',
       view: "form",
       autowidth: true,
       height: document.body.clientHeight,
       width: document.body.clientWidth - 8,
       rows: [
           {
               cols: [
                   {
                       view: 'button',
                       value: 'Начать загрузку',
                       align: 'left',
                       maxWidth: 300,
                       css: 'webix_primary',
                       click: processFiles
                   }
               ],
           },
           {
               cols: [
                   {
                       view: 'button',
                       value: 'Получить данные таблицы миграции',
                       align: 'left',
                       maxWidth: 300,
                       css: 'webix_primary',
                       click: function () {
                           window.open('/migration_data');
                       }
                   }
               ],
           },
        ]
    })
})