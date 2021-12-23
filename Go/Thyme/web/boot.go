package web

import (
	"Thyme/web/extension"
	"Thyme/web/model"
	auth3 "Thyme/web/router/auth"
	chat3 "Thyme/web/router/chat"
	"github.com/gin-gonic/gin"
)

func Init() (*gin.Engine, error) {


	router := gin.Default()

	tableInit()

	routerInit(router)

	return router, nil
}

func routerInit(router *gin.Engine) {
	r := router.Group("/api")

	auth := r.Group("/auth")
	{
		auth.POST("/register", auth3.Register)
		auth.POST("/login", auth3.Login)
	}
	chat := r.Group("/chat")
	{
		chat.POST("/message", chat3.SendMessage)
	}


}

func tableInit() {
	_ = extension.DB.AutoMigrate(&model.User{})
}
