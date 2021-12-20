package web

import (
	auth1 "Thyme/web/auth"
	"Thyme/web/extension"
	"Thyme/web/model"
	"github.com/gin-gonic/gin"
)

func Init() (*gin.Engine, error) {
	router := gin.Default()
	routerInit(router)
	tableInit()
	return router, nil
}

func routerInit(router *gin.Engine) {

	auth := router.Group("/auth")

	{
		auth.POST("/register", auth1.Register)
		auth.POST("/login", auth1.Login)
	}
}

func tableInit() {
	_ = extension.DB.AutoMigrate(&model.User{})
}
